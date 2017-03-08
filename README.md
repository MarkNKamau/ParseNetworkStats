# Parse Network Stats
A java application that parses multiple network statistics outputs into a more readable form. 

##Usage
Actual parsing file can be found [here](https://github.com/MarkNKamau/ParseNetworkStats/blob/master/src/com/marknkamau/NetStatsParser.java).   

####1. Prepare a text file of network statistics formatted as...  
```
Ping statistics for x.x.x.x:  
   Packets: Sent = x, Received = x, Lost = x (x% loss),  
Approximate round trip times in milli-seconds:`  
   Minimum = x ms, Maximum = x ms, Average = x ms
```

Any part not formatted as such will produce an error be skiped. It is therefore possible to include...  
```
Pinging x.x.x.x with 32 bytes of data:  
Reply from x.x.x.x: bytes=x time=xms TTL=x
```  

####2. Select the appropriate locations and names of files.   
Output folder may be left blank.
####3. Select the preferred output formats.  
If no output format is selected, a preview of the data will still be given.

## Screenshots
### Before
<img src="https://raw.githubusercontent.com/MarkNKamau/ParseNetworkStats/master/screenshots/blank.png" width="500"> 
### After
<img src="https://raw.githubusercontent.com/MarkNKamau/ParseNetworkStats/master/screenshots/output.png" width="500">
### Sample outputs
<img src="https://raw.githubusercontent.com/MarkNKamau/ParseNetworkStats/master/screenshots/sample_output.jpg" width="500">  


## Licenses
This application is licensed under Apache 2.0 License.
