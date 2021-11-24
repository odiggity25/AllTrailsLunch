# AllTrailsLunch
Coding challenge for AllTrails that shows a list of nearby restaurants in a map or list view. You can
also use the search toolbar to search for specific restaurants. 

![Map view](https://imgur.com/64Sc4fF)
![List view](https://imgur.com/a/zEStRxU)

--Assumptions/Scope decisions*

There are several areas where I made assumptions about the implementation details. If this were a real
project with real stakeholders, I would have discussed these assumptions with the product manager/
designer to make sure they were correct. Furthermore there are many ways I could have improved
the functionality (re-calculating search results if the user moves the map) however it felt like
it was outside the scope of the assignment, which is mainly to demonstrate understand concepts. I'll
list these assumptions/scope decisions below so you know they were not just overlooked.

-What happens if user moves map? Is search recalculated for new ares?
 -I'm doing nothing currently, mostly because it seemed outside the scope of the assignment
-Should the map update as the user moves?
 -I'm not doing this for scope reasons
-What happens if the user declines the location permission?
 -I'm currently not doing anything besides displaying a toast
-Map/List toggle button should disappear if user starts scrolling down the list and reappear on scroll up
-Filter button doesn't do anything
-Supporting text for each restaurant is just filled with placeholder. I didn't see anything obvious
 in the json response that I should use to populate this with
-It should remember the last map location so it doesn't pan from 0,0 every time the app opens
-The results should be paginated, currently I'm only showing the first page of results
-There is no testing, I would definitely add some UI tests before merging this in real life. I tried
 adding some unit tests but unfortunately I ran into some issues mocking the koin dependencies and
 would need more time to figure out why.
-UI is definitely not perfect. There are lots of little tweaks to make (font, margins, etc.). It's 
 pretty close, but I also wasn't able to inspect the inVision prototype so I had to eyeball it all.
-I've only tested this on two devices. I didn't even consider tablets or landscape, or dark mode.

--PROJECT STRUCTURE

I like to structure my project by function or domain, rather than by type. For example instead of having:
-views
  -RestaurantListFragment
-viewModels
  -RestaurantsListViewModel
-repositories
  -RestaurantsRepository

 instead have:
 -restaurants
   -RestaurantRepository
   -search
     -list
       -RestaurantListFragment
       -RestaurantsListViewModel

It seems more intuitive to structure by type, however in practice I have found it's never very useful. It makes
finding the file you're looking for a lot easier because you can drill down based on domain.
and you are far more likely to want to see files related to a particular file rather than other files of the
same type.

--DOCUMENTATION

You'll notice that there is fairly limited documentation throughout the code.
I'm a big believer that your code should be self documenting and documentation should only be
added where it can't be made obvious from the code what is happening. Two exceptions to this are:

1. I like to add documentation to the top of view and view model classes. This is so if someone lands on a
file for the first time, they can get an idea of where the file is used in the app without having to read
through the implementation details or navigate to the layout file.

2. I've added several "Note to reviewer:" comments. This is just for things that might be different than from
what you, the reviewer, have seen before. These comments wouldn't normally be included because I would
have discussed these approaches with the rest of the android developers to make sure everyone is okay
with it and are on the same page.
