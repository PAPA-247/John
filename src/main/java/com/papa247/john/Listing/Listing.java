/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.Listing;

import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Enumerators.ListingType;
import com.papa247.john.User.User;

public class Listing {

    public Address address;
    public int id;
    public String appartmentNumber;
    public double monthlyPrice;
    public double rentLength;
    public User owner;
    public Lease leaseAgreement;
    public Room[] rooms;
    public Room[] bedrooms;
    public Amminities[] amminities;
    public ListingType listingType;
    
    
    public double getTotalSize() {
        double size = 0;
        for (Room room : rooms)
            size += room.size;
        return size;
    }

    
}
