# Summary
** CURRENT A WORK IN PROGRESS **
This collection of files is a learning exercise in using data structures and algorithms to manage set of files of incoming data.

## Application
You are receving data file with GPS coordinates and timestamps showing where animals are located over time (i.e. https://www.ocearch.org). You will not get duplicate data and no files will have overlapping time ranges. The data in the file may not be sorted. You want to manage the files such that you can get all data for a particular animal, search for data by time range, and analyze the path taken by the animal (i.e. detect cycles). In some cases, you may want to get the latest information first, like knowing the location of a shark for swimming advisorys. In other cases you may want to have chronolicial data where you process the oldest files first, as when you are trying to retrace the path to follow the animal in near real time.

**Processed vs. Unprocessed:** For this example, a processed file is one which is searchable along with the other files. An unprocessed file which has just been received, isn't yet searchable.

## Topics covered
* Hash maps
* Lists
* Queues
* Stacks
* Sorting
* Graphs

# Disclaimer
This is not meant to be prescriptive or even practical. I'm hoping to only show how data structures can be used in combination instead of in isolation as in traditional coding interview questions.