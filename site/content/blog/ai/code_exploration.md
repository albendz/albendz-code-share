---
title: How I’m Using AI For Code Exploration
description: How I'm using AI to explore codebases, what can go wrong, and whether it will replace us.
date: 2025-09-08
updated: 2025-09-08
tags: ai
---

# How I’m Using AI: Code Exploration

In this post I will go over how I’m using AI coding tools to learn about and explore codebases. I’ll go through some examples, things to watch out for, and how this use of AI will evolve the jobs of software engineers.

## The AI

The AI I’m referring to is LLM-based coding agents such as [Claude](https://www.anthropic.com/claude-code), [Copilot](https://github.com/features/copilot), or [Gemini](https://codeassist.google/). I’m using the AI in [interactive mode](https://docs.anthropic.com/en/docs/claude-code/interactive-mode) via the [command line interface](https://docs.anthropic.com/en/docs/claude-code/cli-reference) with a question and answer interaction with the agent.

## The Codebase

Using AI for code exploration can be done on any codebase that the AI can understand and access. What does it mean for the AI to be able to understand the codebase? The AI has to be at least trained on code using the same programming language. If you have custom tools, patterns, or structures in your codebase, you can provide details about how those are used and how they work as an initial prompt, in configuration files, or with [fine-tuning techniques](https://aws.amazon.com/blogs/machine-learning/best-practices-and-lessons-for-fine-tuning-anthropics-claude-3-haiku-on-amazon-bedrock/).

In order to search the codebase, you need to make sure you have all the relevant code you’re exploring in the environment the AI is working with. I run my coding agent locally in a [monorepo](https://en.wikipedia.org/wiki/Monorepo), which conveniently makes a lot of code available all at once. You may need to pull down additional repositories for your code exploration.

## A Fellow Explorer

I use AI to help me explore codebases when I have to start working in an unfamiliar codebase. Using AI in this case can get me answers quickly and save me from asking obvious questions to my coworkers. By having the AI agent output the conversation to a file, I can also create documentation for my own reference or to share with others.

I also use AI in code exploration when I’m exploring multiple paths at the same time such as the context of the call sites of a given method. The AI can keep track of each call site, the context, and use that information to help me filter or further explore the use of the method.

## Example: Who’s Calling?

In this example, I show an approximate interaction with Claude to explore an API, it’s callers, and potential bad practices of callers to the API. The output is not exactly what Claude gave me as I’ve edited it to remove any references to real applications.

**Prompt 1**: There is a service called Music Library. The API definitions are in monorepo/musiclibrary/thrift and the implementation of the APIs is in monorepo/musiclibrary/src/main Please summarize each API split by [HTTP method](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods).


```
Music Library API Summary

The Music Library service is music library management service that provides both Thrift APIs for managing libraries of audio files (music).

Thrift APIs (Primary Interface)

The main API interface is defined via Thrift at /monorepo/musiclibrary/thrift/api.thrift with the following operations:

GET Operations (Read-only)

- getLibrary - Retrieve a single music library by ID or user
- getLibraries - Bulk retrieve multiple libraries
- getLibrariesForSong - Get all libraries containing a song

POST Operations (Create/Update)

- createLibrary - create a new library with an initial set of songs
- addSong - Add a single item to an existing library
- bulkAddSong - Add multiple songs to an existing library

DELETE Operations

- deleteLibrary - delete a library
- deleteLibrarySong - Remove a specific song from a library
```

At this point, the results will be similar to a OpenAPI (https://en.wikipedia.org/wiki/OpenAPI_Specification)  spec. At this point, you can use the output of the first prompt to update and populate either your existing documentation or have this serve as your initial API documentation

**Prompt 2**: For any of the write operations that support bulk uploads or large payloads, are there any size limits on the APIs? What is the behavior when the size limits are exceeded and at what point during request handling will this behavior be enforced.

```
Size Limit Analysis

Based on my analysis of the Music Library service code, here are the size limits for write operations that support bulk uploads or large payloads:

Size Limits

Library Item Limits

- Maximum library items: MAX_LIBRARY_ITEMS (defined in MusicLibraryConfiguration.kt:15)
- Affected Operations:
    - createLibrary - New library creation
    - addSong - Single song add
    - bulkAddSong - Bulk song add

Input Size Limits

- getLibraries API: Configurable via dynamic config
    - Config path: music_library.get_libraries.api_limits
    - Default limit: GET_LIBRARIES_MAX_INPUT_SIZE = 500
    - Applied to: request.getLibraryIds() list size

Behavior when limits exceeded

Exception Thrown

When size limits are exceeded, the service throws:

RequestTooLargeException (HTTP 413 - Request Entity Too Large)

Error messages

- Library Items: “[createLibrary] Number of songs ${request.songs.size} exceeds limit”
- Input size: “[$api] Input size exceeded inputSize = [${request.libraryIds.size}]”

Enforcement Points in Request Handling

1. Early Request Validation (Before Database Operations)

Library Creation (LibraryController.kt:147-152)

if (request.songs.size > MAX_LIBRARY_ITEMS) {

throw new RequestTooLargeException(/* error message */)

}

Bulk Operations (LibraryController.kt:263-272):

if (request.libraryIds.size > LibraryCnfiguration.getLibrariesMaxInputSize) {

throw new RequestTooLargeException(/* error message */)

}

1. Pre-database write validation

Add operations( LibraryService.kt:387-392, 460-465):

- First fetches existing library from database
- Checks if current size ≥ MAX_LIBRARY_ITEMS
- Throws RequestTooLaergeException before attempting to append

This two-phase validation approach:

1. Input Validation happens immediately after request parsing
2. State validation happens after reading current library state but before writes

The enforcement occurs early in the request pipeline - before any expensive database write operations are attempted, which provides efficient fail-fast behavior and prevents resource waste on oversized requests.
```

Now we’re getting into scenarios where we can use natural language to narrow our search and start asking about internal implementation details.

**Prompt 3**: There are multiple clients of these APIs located in the following directories: monorepo/user, monorepo/workflow, monorepo/admin. For each of these clients, look for usages of the APIs that have limits enforced and summarize how the limit enforcement is handled. In cases where there are no limits or no enforcement of limits, indicate whether the calling code has limitations.

```
Music Library Service Client Analysis

Based on my analysis of the codebase, here are all the clients calling Music Library and their error handling patterns:

Client Summary:

1. User Service - /monorepo/user

Error Handling:

- Pattern: Defensive error handling with fallback behavior
- Exception handling:
    - isLibraryValid(): Catches all exceptions, logs error, and throws RuntimeException - causes the request to fail
    - getLibrary() & getLibrarySongs(): catches all exceptions, logs error, and returns null/empty results - graceful degradation
- Pre-request validation: ❌ none found - No size checks before calling Music Library APIs
- Key Code:


// Failure mode: Throws Exception
catch (Exception e) {
  log.error("[Music Library] getLibrary request failed", e);
  throw new RuntimeException("Fail to verify libraryId", e);
}

// Graceful degradation mode: Returns empty results
catch (Exception e) {
  log.error("[Music Library] getLibrary request failed, e);
  return Collections.emptyMap();
}  


1. Music Library Workflows - /monorepo/workflow

Error handling:

- Pattern: fail-fast with detailed logging
- Exception handling: Catches exceptions, logs detailed context, and re-throws them to propagate failures upstream
- Pre-request validation: ❌ None found - No size limits checked before bulk operations
- Validation present: ✅ Song validation - Validates individual library songs:
    - Checks for null song ID
    - Validates songs IDs are non-empty, non-null Strings and exist in librarySongIds
- KeyCode:


catch(Exception e) {
  log.error("[{}] Error creating library for userId {}", this.getClass().getSimpleName(), userId, library.getId(), e);
  throw e; // Re-throws to fail the operation
 }


1. Admin Service - /monorepo/admin

Error Handling:

- Pattern: Minimal error handling - relies on framework defaults
- Exception Handling: ❌ No explicit error handling - uses getLibraries without catch blocks
- Pre-request Validation: ❌ None found - No size checks before API calls
- Key Code:


// No try-catch blocks
return musicLibraryApiClient.getLibraries(request);

Key Findings

Error Handling for Music Library Size Limit Exceptions

RequestTooLargeException (HTTP 413) Handling:

1. User Service:
    1. ✅ Imports RequestTooLargeException in test files
    2. ❌ No specific handling in production code - caught by generic Exception handler
    3. Behavior: Will throw RuntimeException on size limits, causing request failure
2. Music Library Workflows:
    1. ❌ No specific handling - caught by generic Exception handler
    2. Behavior: Re-throws exception, causing operation failure
3. Admin
    1. ❌ No error handling at all
    2. Behavior: Exception bubbles up through request handler chain

Pre-request validation patterns

Size Validation before Music Library calls: ❌ NONE FOUND

- No clients perform size checks before calling Music Library APIs
- No clients replicate Music Library’s validation logic (e.g., MAX_LIBRARY_ITEMS checks)
- Clients rely entirely on Music Library service to enforce size limits
- No defensive programming against RequestTooLargeException

Critical Gap: All clients are reactive rather than proactive - they wait for Music Library service to reject oversized requests rather than preventing them upfront. This leads to unnecessary network calls and late error detection in the request processing pipeline.
```

Finally, at the third prompt, I found a case where the AI can do this much faster than I can. I could do a code search in each project or repository to look for the handful of APIs being called and then read the code for each call and then find out where the code could be improved. By this point in the interaction, I’ve learned about how we’re protecting our service (or not) from large requests as well as the patterns for how this is generally handled.

I’ll mention this in caveats as well - if the AI says there’s no validation or protection on these APIs, that’s not necessarily true. The service might be using something like [AWS API Gateway](https://docs.aws.amazon.com/apigateway/latest/developerguide/welcome.html) or [nginx](https://betterstack.com/community/questions/default-nginx-client-max-body-size-value/) that limit requests sizes prior to the requests making it to your service. In this case, you need to be aware of the context of the service as well as leverage your knowledge of system architecture to know there may be other related implementations that may or may not be configured in code. This additional context can be provided as additional prompt information or via external context like an [AWS Cloudformation MCP](https://modelcontextprotocol.io/docs/getting-started/intro) server to read your [service configuration](https://awslabs.github.io/mcp/servers/cfn-mcp-server/).

## Cautions and Caveats

Every use of AI comes with cautions and caveats. Here are some things to keep in mind when using AI for code exploration:

- *What but not why*: AI can fairly confidently tell you what you’re looking at but it may not have enough information about why it was written that way. It can make guesses but they can be wildly inaccurate. For example, it can tell you that a specific variable is being lazy loaded and it can tell you that it may sometimes help with performance. However, since it doesn’t know how well the code performs at runtime and it doesn’t know the real-life latencies associated with this lazy loading, you won’t know for sure why this variable is being lazy loaded.
- *Out of sight, out of mind*: The AI only looks at what it thinks is related and what you told it to look at. Unless you specifically ask it to look elsewhere, it may miss something critical. In the example where you can search for API usages across different services and client codebases, I told it where to look specifically. I also could have generically said “all service clients” and it may miss scripts or web clients.
- *Shallow vs. deep*: This type of AI is great at summarizing content and that’s exactly what code exploration with AI is doing. However, summaries can leave out critical details. It is important to both follow up with more specific questions and verify anything that is affecting decisions by checking the code yourself.
- *Generalized knowledge*: This use of AI works best when used with codebases similar to what the model was trained on. If the AI encounters something different, it will be ranked with lower probabilities and the AI may skip analyzing it. This can result in a poor understanding of the code and large gaps in how it describes the code.

## Will AI Replace Us?

This use of AI isn’t so much something that will replace us as something that will help us work faster. You can provide a lot of details in prompts to make the responses more accurate and useful but at some point, a human still needs to combine those results with their knowledge and analytical skills to make final decisions. As mentioned in the caveats, the AI can still be wrong or miss critical information that we as humans know. Perhaps one day, as we extend what the AI can leverage using tools like MCP servers, these gaps will close.

The AI certainly won’t replace us when it comes to writing docs - developers don’t write or update them most of the time anyway.