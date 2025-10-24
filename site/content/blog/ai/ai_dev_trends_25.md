---
title: AI in Software Development 2025
description: A review of top themes in the evolution of AI in Software Development in 2025.
date: 2025-10-24
tags: ai,conference
---

## AI in Software Development 2025

This year I attended the [Tech Futures](https://lwtsquad.com/techfutures2025/) conference by [Lesbians Who Tech](https://lwtsquad.com/about/) and [Women Who Code](https://womenwhocode.com/). This conference focused on AI and how it impacts the different phases and roles of software development. This post is about the content, trends, and how I see the ideas impacting software development.

### Trend 1: Engineers as AI Managers

Ever since we saw that AI can write code, the industry started talking about AI replacing developers. This led to concerns about whether software engineers will have jobs. As time went on, we learned that AI generated content is not good enough for AI to run unsupervised and the trend narrowed down to AI being able to replace junior engineers rather than all engineers. This gets us into the first trend: engineers as AI managers.

Before expanding on that, I’ll share a phased model that repeated throughout the conference about how use of AI evolves for developers:

1. **Single engineer, AI assistant**: engineers use AI as an assistant that's guided at each step.
2. **Single engineer, collaboration with an AI agent**: an AI agent runs alongside an engineer independently, carrying out specific, well-defined tasks (ex. AI code reviewer, AI unit tester).
3. **Engineering team and autonomous AI agent ecosystem**: there are multiple AI agents running autonomously for code reviews, debugging data collection, etc. that integrate with processes in software development

Going back to *engineers as AI managers*: each phase requires to develop skills defining prompts, sub-tasks, and success criteria such that AI can run independently and effectively - they become AI managers.

This trend matches what I’m seeing companies do to make AI more effective. Companies are going beyond having a code assistant available to building autonomous agents running as part of automated processes. I’ve also noticed that the most effective AI users are getting better at describing tasks, context, and scope for the AI such that it is able to successfully complete tasks. That leads us to the second trend.

### Using AI is a Skill

From the beginning of LLMs being available to consumers, people have said “just ask AI and it will do it for you”. This led to ["AI Slop"](https://en.wikipedia.org/wiki/AI_slop) and complaints that it takes longer to fix the AI code than to write it yourself. There is a repeating pattern:

1. Engineer is told to use AI to code faster
2. Engineer tries AI
3. AI produces slop code
4. Engineer abandons AI

The missing piece: using AI is a skill. Whether or not you want to use AI, you need to learn to define scope, instructions, and success criteria for the AI assistants and agents to be effective in your environment. Earlier this year, I was skeptical about whether AI writing write code would have the efficiency benefits people were talking about. However, I also saw that engineers would be *expected* to use AI. So, I sat down and tried using AI every time I started a task. Through trial and error, I learned how to use it more effectively and break down work such that subtasks could be done by AI independently.

A critical point: AI users have to understand both the problem and solution in order to use AI effectively. Junior engineers, new coders, or non-coders who do not have the knowledge or skills to tell the AI how to build a good solution are surprised when it turns out their code is slop.
Companies and teams are sending out regular reminders that you are still responsible for the code AI is writing for you, just like as a manager, you are responsible for the actions taken by your team.

### AI Finds the Next Bottleneck

We’ve been focused on how AI can do development work and it can help us do it faster. However, when you look at the overall software delivery process, it’s not just coding. Once we start producing more code, we look to the right and find the next bottleneck - code reviews.

Okay, so let’s have AI do code reviews. Great, now we’re both writing and merging code faster. Now what about managing releases? The new bottleneck is deployments, especially if you’re on a team that does weekly or less frequent deploys. On top of that, there are more changes per deployment, more risk in each deployment, and higher complexity debugging production issues that result from deployments. At some point, you find where the humans can’t keep up or where quality starts to take a hit.

This also works in the other direction, going left: if developers are writing code more quickly, the product team needs to produce requirements more quickly to prevent idle developers (if they're not too busy with the AI-related production issues). In this case, you can also use AI to help build requirements documents with automated data analysis and quickly filling any gaps.

The point: AI only helps you deliver as fast as your next bottleneck and organizations need to look at the end-to-end gains in the software development lifecycle, not just the gains in a single phase.

### AI Governance

Speaking of bottlenecks, we have my favorite delivery timeline wildcard: security and privacy reviews. Pretty much any application that has identifiable user data in any form is going to need a security review, legal review, and compliance plan regardless of whether it uses AI or not. As AI moves away from being a POC tool, we’re formalizing responsible and compliant uses of AI.

I’ve seen this play out: Product teams and development teams propose an AI-based solution and they are surprised to find out the new rigor needed to launch that product - if they even can. Here are some of the things we are now required to take into consideration:

- Justify what aspects of the problem require use of AI?
- How will we continuously monitor and adjust the use of AI to be compliant and secure?
- What guardrails are we using to maintain compliance, protect users, and protect the company?
- What is the escalation plan in the event of a non-compliance or security event involving the AI solution?
- Which teams are responsible for each aspect of maintaining the AI solution and AI compliance?
- Are we using AI models and tools that have been reviewed by security and legal teams?

When it comes to these rigorous processes, organizations need to consider the balance of speed and quality. If you make it too hard to use AI solutions in products or development, developers will find ways to use it anyway - now it's [*shadow AI.*](https://www.ibm.com/think/topics/shadow-ai) This means organizations need to think carefully about when these governance reviews are really needed and create fast-tracks for low-risk usecase.

### Final Thoughts

I am happy to see that AI is no longer being viewed as a magical solution that you can carelessly throw at any problem. The industry has passed the POC and exploration phase and moved into using AI as a fully supported, well-thought-out tool in the development process. With these emerging perspectives and investments, I have more confidence in AI being effective yet developers still having a key role in critically thinking about how to maintain AI effectiveness in large-scale software development organizations.