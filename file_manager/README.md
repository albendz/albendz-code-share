# Summary

I've created a demo using various data structures and algorithms to provide material
for a small talk on using data structure and algorithms in Python. Below is what's convered.
This is continually changing as I try to make it better.

## Application
You are receiving data file with GPS coordinates and timestamps showing where animals are located over time (i.e. https://www.ocearch.org). You will not get duplicate data and no files will have overlapping time ranges. The data in the file may not be sorted. You want to manage the files such that you can get all data for a particular animal, search for data by time range, and analyze the path taken by the animal (i.e. detect cycles). In some cases, you may want to get the latest information first, like knowing the location of a shark for swimming advisories. In other cases you may want to have chronological data where you process the oldest files first, as when you are trying to retrace the path to see which paths are most common.

## Data Format
Data received in this example is in the following format:
<ISO timestamp>,<long>,<lat>

I am using non-standard geolocation notation for convenience. I'm also treating a "location" as a very specific single point, which is not practical in applications using geolocation data. All this data can be converted on ingestion or when being sorted, for example, into a format that makes sense. This can also mean making the data less specific. For example, the ISO timestamp is down to the millisecond but we could sort and group data that is within the same hour instead.

## Topics covered
* Hash maps
* Lists
* Queues
* Stacks
* Sorting
* Graphs
* Search (Binary and Graph)

Documentation is under construction. The plan is to describe each implementation and how it is used within the context of the application.

### Hash Map
At a high level, we have a set of animals (or sharks) and each shark has a set of files. This uses a hash map (or dictionary) to map shark name to a set of files. Here it's called AnimalTracker. You can add animals to track and
use the Animal Tracker to list animals, get data on each animal, and execute actions against the data for that animal.

## Terms
Some terms I use may not make sense to everyone so I will try to explain those here.

**Raw data:** I use this to describe data that hasn't been looked at yet. This means it is in its original
received form.

# Running The Code  
To run this code, you will need Python 3.7. It's been developed on both Mac and Windows so I hope it works in at least one of those environments.

To run the demo (which is still under construction): `python demo.py`

# Running Tests
To run the unit tests, use the command `python -m unittest test_*` to run all the tests in the top level directory. At some point, I plan to restructure the code so it's less flat and can run tests a little more nicely.

# Disclaimer
This is not meant to be prescriptive or even practical. I'm hoping to only show how data structures can be used in combination instead of in isolation as in traditional coding interview questions.

# TODOs
- Generate pydocs
