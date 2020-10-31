Milestone 1


Holy balls, it works.*


* If buggy and held-together-with-duck-tape-and-chewing-gum is considered working.


There's a lot of changes here all within the last day, (or two? Can't remember)



What works (_what this was focused on_):
	Adding addresses
	Removing addresses
	Adding listings
	Removing listings
	Lease management (users/signing a little buggy)
	Editing leases a little buggy (price not saving correctly)





App:
	Make the MainWindow larger on startup
	Set GUI mode back.

ConsoleUI:
	Added example commands and what not. It now has 3 basic 'commands' setup. Only one works, I had other stuff to complete.
	The ground work is laid is what I'm trying to say.


DataBases:
	Ooof. DataBases API now allows address/listing/lease creation via the GUI.
	You can now edit Addresses, Listings, and Leases using three methods. (You can also create them via DataBases)
	Reworked how address/listings are added. The internal listings array is now a "slave" to the listings within addresses. **DO NOT** add listings directly to this array, instead add it to an address.

Lease & Signer:
	Reworked these a bit. They work is all I got.
	Signer now its own class file.
	Leases can be "locked" (when a user signs the lease it becomes "locked"): meaning no one can added themselves to the lease

Listing:
	GUI methods and null checking.
	Fix naming error with JSON conversion.

Room:
	Initialize variables.
	Fix a naming error with JSON conversion.

Address:
	

MainWindow:
	Listings host added in.

ListingsHost & Session:
	Session now stores a method that can change which listings are displayed.

Session:
	.isLoggedIn() method to quickly check if a user is logged in

Range, Coordinate:
	Fix initializers

StringUtils:
	.isNullOrEmpty(): quickly check a string if null / empty

SelecWindow:
	You can now select an address, listing, or user (or even multiple of those) and get which values were selected in code.
	(Used when making a listing to select an address)
	Documentation to come.


Address & Listing & Lease windows:
	Works. 'nuff said.


Search & SearchPannel:
	DataBases searching doesn't work yet... no idea why.
	SearchPannel added.


SetAminities:
	Works.

