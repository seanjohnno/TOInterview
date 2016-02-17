# What is it?

A bit of a dirty demo! It scrapes the TimeOut website and groups section/zone titles along with images and their titles. It works for pages with 'Zones' and 'Slideshows'. All page types aren't handled so you *should* see an error page "Error - We don't have a handler for this yet". For content it can handle you should see galleries of pictures (Bars & Pubs / Half-Term in London, stuff like that)

# What does it demo?

* Hopefully my ability to code and use a bunch of standard Android stuff; Activity/Fragment/Loaders
* Usage of the new(ish) RecyclerView
* Custom 'View' development to keep the aspect ratios of the images right (4:3 and 16:9 on the website)
* Some Animation
* A little bit of RX, the threading stuff is useful
* Integration with some other handy 3rd party libs; Volley, Picasso, Dagger

# Caveats

* Only tested on a HDPI (Moto G 2nd gen) 5.0.2 so most of the assets are just for that at the moment, I haven't bothered with sw-layouts for the demo (sorry!)
* As mentioned above, the engine doesn't parse all page content so you should get an error page if it can't handle it
* The scraper is a bit dirty, different sequences were throwing regex and state machines out so that's why I went with the fall-through approach I did

# What's left?

* I was asked to submit today so I had to rush a bit. I would have liked to have done more unit testing on the UI
* To figure out what the other page formats are and what I can display for them
