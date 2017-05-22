# Parse Network Stats
A java application that parses multiple network statistics outputs into a more readable form.  
It can output in .txt, .csv or .json.

## Usage
Actual parsing file can be found [here](/src/main/java/com/marknkamau/NetStatsParser.java).   

#### 1. Prepare a text file of network statistics formatted as...  
```
Ping statistics for x.x.x.x:  
   Packets: Sent = x, Received = x, Lost = x (x% loss),  
Approximate round trip times in milli-seconds:`  
   Minimum = x ms, Maximum = x ms, Average = x ms
```

Any part not formatted as such will produce an error be skipped. However, it may still cause errors in the output.
  
#### 2. Select the appropriate locations and names of files.   
Output folder may be left blank.


#### 3. Select the preferred output format(s).

## Screenshot
<img src="https://raw.githubusercontent.com/MarkNKamau/ParseNetworkStats/master/screenshots/main.png" width="500">   

## Licenses
This application is licensed under Apache 2.0 License.
