
public class Song {

	private String songName; //name of the song
	private String artistName; //name of the artist
	private int songID; //ID number of the song
	private int[] stationPlays; //array of each station and number of times this song is played on each
	
	
	/**
	 * @return The name of this song.
	 */
	public String getName() {
		return songName;
	}
	/**
	 * @param name A name for this song.
	 */
	public void setName(String name) {
		songName = name;
	}
	/**
	 * @return The artist that poured their heart and soul 
	 * into making this work of art. Don't pirate music! 
	 * You wouldn't download a bear.
	 */
	public String getArtist() {
		return artistName;
	}

	/**
	 * @param artist An artist for this song.
	 */
	public void setArtist(String artist) {
		artistName = artist;
	}
	/**
	 * @return Returns the Song Id. 
	 */
	public int getID() {
		return songID;
	}
	/**
	 * @param iD Set a Song Id
	 */
	public void setID(int iD) {
		songID = iD;
	}
	/**
	 * @return  An array the length of the number of radio stations. 
	 * stationPlays[i] should be the number of times this song is played on 
	 * the station with station id equal to i. 
	 */
	public int[] getStationPlays() {
		return stationPlays;
	}
	/**
	 * @param n Increase number of times station n played this song
	 */
	public void incrementStationPlay(int n) {
		stationPlays[n]++;
	}
	
	/** Constructor. 
	 * @param name Name of this song
	 * @param artist Artist of this song
	 * @param ID Id of this song
	 * @param numStations The total number of radio stations.
	 */
	public Song(String name, String artist, int ID, int numStations) {
		songName = name;
		artistName = artist;
		songID = ID; 
		stationPlays = new int[numStations];
		
	}
	
	/** Calculates basic statistics about this current song and returns it in an array of integers. 
	 * The order of the calculations should be: 
	 * <ol>
	 *  <li>Average number of plays on each station that carries it, 0 if song is never played</li>
	 *  <li>Total number of plays across all stations</li>
	 *  <li>Station that plays this specific song most often, 
	 *  -1 if the song is played the same number of times across all stations</li>
	 *  <li>Maximum number of plays on one station</li>
	 *  <li>Station that plays this specific song the least
	 *  -1 if the song is played the same number of times across all stations</li>
	 *  <li>Minimum number of plays on any one station</li>
	 * <ol>
	 * If the song is never played, the average in (0) should be set to 0. 
	 * For all calculations except for the average, we can count stations that do not carry this song.
	 * That is the maximum and minimum number of plays can be 0.
	 * If multiple stations play this song the same number of times, use the station with the lowest station id for (2) and (4). 
	 * If no stations play this song, (2) and (4) should be set to -1 since our station ids are zero-based. 
	 * @return An array of basic song statistics 
	 */
	public double[] getStatistics(){
		// Take care of the base case scenario
		if (stationPlays == null || stationPlays.length == 0) {
			return null;
		}
		
		/* Calculate the average plays of this song. Two counters: number of stations that have played this song, and
		 * total number of plays. If stationPlays[i] is more than zero, then increment the first counter and add 
		 * stationPlays[i] to the second counter. Finally, average out the two. 
		 * While iterating, update max/min station/number of plays.
		 */
		double stationsThatPlayed = 0; // number of stations that played this song
		double totalPlays = 0; // total plays of this song
		
		double maxNumberOfPlays = stationPlays[0]; //max number of times played on one station
		double maxStation = 0; //station that played this song the most
		
		double minNumberOfPlays = stationPlays[0]; //min number of times played on one station
		double minStation = 0; //station that played this song the least
		
		for (int i = 0; i <stationPlays.length; i++) {
			// if the song is played once or more on a station, include it in the average
			if (stationPlays[i] > 0) {
				stationsThatPlayed++;
				totalPlays += stationPlays[i];
			}
			if (stationPlays[i] > maxNumberOfPlays) {
				maxNumberOfPlays = stationPlays[i];
				maxStation = i;
			}
			if (stationPlays[i] < minNumberOfPlays) {
				minNumberOfPlays = stationPlays[i];
				minStation = i;
			}
		}
		
		double[] statsArray = new double[6];
		// If the song is never played, return [0,0,-1,0,-1,0]; otherwise return stats
		if (totalPlays == 0) {
			statsArray[0] = 0; statsArray[1] = 0; statsArray[2] = -1; 
			statsArray[3] = 0; statsArray[4] = -1; statsArray[5] = 0;
		}
		else {
			statsArray[0] = totalPlays/stationsThatPlayed;
			statsArray[1] = totalPlays;
			statsArray[2] = maxStation;
			statsArray[3] = maxNumberOfPlays;
			statsArray[4] = minStation;
			statsArray[5] = minNumberOfPlays;
		}
		return statsArray;
	}
	
	@Override
	public String toString() {
		return songID + ". " + artistName + " - " + songName;
	}
	
}
