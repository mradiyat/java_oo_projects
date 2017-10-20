import java.io.FileNotFoundException;
import java.util.Scanner;

public class RadioRecommenderSystem {
	
	private Song[] songArray;
	private Station[] stationArray;
	
	/**
	 * Initializes the Parser and the RadioRecommenderSystem. Asks for user input through the console afterwards.
	 * Should keep asking for input indefinitely. The user can input the following commands:
	 *   similarsong <song ID>          - Finds the most similar song to the chosen song.
	 *   stats <song ID>                - Prints statistics of the chosen song.
	 *   recommend <station ID>         - Recommends a song to the chosen station.
	 *   similarradio <station ID>      - Finds the most similar radio station to the chosen station.
	 *   exit                           - Exits the program.
	 * 
	 * @param args The first argument should contain the folder path for the three files. 
	 */
	public static void main(String[] args) {
		try {
			Parser parser = new Parser("songs.txt", "stations.txt", "playlist.txt");
			RadioRecommenderSystem x = new RadioRecommenderSystem(parser.getSongs(), parser.getStations());
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("similarsong <song ID> - Finds the most similar song to the chosen song.\n"
						+ "stats <song ID> - Prints statistics of the chosen song.\n"
						+ "recommend <station ID> - Recommends a song to the chosen station.\n"
						+ "similarradio <station ID> - Finds the most similar radio station to the chosen station.\n"
						+ "exit - Exits the program.");
				String currentLine = scanner.nextLine();
				String[] R = currentLine.split(" ");
				try {
					if (R[0].equals("exit")) {
						System.exit(0);
						break;
					}
					if (R[0].equals("similarsong")) {
						System.out.println(x.closestSong(Integer.parseInt(R[1])));
						continue;
					}
					if (R[0].equals("stats")) {
						double[] statsArray = x.songArray[Integer.parseInt(R[1])].getStatistics();
						String statsString = "[";
						for (int i = 0; i < statsArray.length; i++) {
							statsString += statsArray[i] + ", ";
						}
						System.out.println(statsString + "]");
						continue;
					}
					if (R[0].equals("similarradio")) {
						System.out.println(x.closestStation(Integer.parseInt(R[1])));
						continue;
					}
					System.out.println("Invalid input, please try again");
					
				}
				catch (Exception e) {
					System.out.println("Invalid input, please try again");
				}
				continue;
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}


	/**
	 * Constructor. Should store a local copy of the arrays.
	 * @param songs
	 * @param stations
	 */
	public RadioRecommenderSystem(Song[] songs, Station[] stations) {
		songArray = songs;
		stationArray = stations;
	}

	/**
	 * Returns the song which is most similar to the song with the corresponding songID.
	 * In the case of a tie, return the song with the lowest ID.
	 * @param songID ID of the original song
	 * @return -1 if the given songID is invalid
	 *         -2 if there are not enough songs 
     *          otherwise the ID of the most similar song 
	 */
	public int closestSong(int songID) {
		if (songID < 0 || songID > songArray.length - 1) {
			return -1;
		}
		
		if (songArray.length == 1) {
			return -2;
		}
		
		int closestSong = (songID == 0 ? 1 : 0); //initialize first song, and make sure it's not the same as songID
		//iterate over all songs. If current song is more similar to songID than closestSong, update closestSong 
		for (int i = 0; i < songArray.length; i++) {
			if (i != songID) {
				 if (songSimilarity(songArray[i], songArray[songID]) > songSimilarity(songArray[closestSong], songArray[songID])) {
					 closestSong = i;
				 }
			}
		}
		return closestSong;
	}

	/**
	 * Computes the similarity of two given songs
	 * @param s1 First song
	 * @param s2 Second song
	 * @return Double representing the similarity between the songs
	 */
	public double songSimilarity(Song s1, Song s2) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		
		if (s1.equals(s2)) {
			return 1;
		}
		/* The formula is [sum(product(stationPlay(i1), stationPlay(i2)))] divided by 
		 * [product(root(sum(stationPlay(i1)^2)), root(sum(stationPlay(i2)^2))]
		 * Goal of this portion is to maintain variables to hold each of these
		 */
		double sumOfProducts = 0;
		double sumOfSquares1 = 0;
		double sumOfSquares2 = 0;
		
		int[] s1plays = s1.getStationPlays();
		int[] s2plays = s2.getStationPlays();
		
		for (int i = 0; i < s1plays.length; i++) {
			sumOfProducts += (s1plays[i] * s2plays[i]);
			sumOfSquares1 += (s1plays[i] * s1plays[i]);
			sumOfSquares2 += (s2plays[i] * s2plays[i]);
		}
		
		//return 0 if one of the songs is never played. This is signified by sum of squares of plays being 0
		if (sumOfSquares1 == 0 || sumOfSquares2 == 0) {
			return 0;
		}
		
		return sumOfProducts / (Math.sqrt(sumOfSquares1) * Math.sqrt(sumOfSquares2));
	}

	/**
	 * Returns the station which is most similar to the station with the corresponding radioID.
	 * In the case of a tie, return the station with the lowest ID.
	 * @param radioID ID of the original radio station
	 * @return -1 if the given radioID is invalid
	 *         -2 if there are not enough stations 
     *          otherwise the ID of the most similar station
	 */
	public int closestStation(int radioID) {
		if (radioID < 0 || radioID > stationArray.length - 1) {
			return -1;
		}
		
		if (stationArray.length == 1) {
			return -2;
		}
		
		int closestStation = (radioID == 0 ? 1 : 0); //initialize first station, and make sure it's not the same as radioID
		//iterate over all stations. If current station is more similar than closestStation, update closestStation
		for (int i = 0; i < stationArray.length; i++) {
			if (i != radioID) {
				 if (stationSimilarity(stationArray[i], stationArray[radioID]) > 
				 stationSimilarity(stationArray[closestStation], stationArray[radioID])) {
					 closestStation = i;
				 }
			}
		}
		
		return closestStation;
	}

	/**
	 * Computes the similarity of two given stations
	 * @param s1 First station
	 * @param s2 Second station
	 * @return Double representing the similarity between the stations
	 */
	public double stationSimilarity(Station s1, Station s2) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		
		if (s1.equals(s2)) {
			return 1;
		}
		/* The formula is [sum(product(songPlay(i1), songPlay(i2)))] divided by 
		 * [product(root(sum(songPlay(i1)^2)), root(sum(songPlay(i2)^2))]
		 * Goal of this portion is to maintain variables to hold each of these
		 */
		double sumOfProducts = 0;
		double sumOfSquares1 = 0;
		double sumOfSquares2 = 0;
		
		int[] s1plays = s1.getSongPlays();
		int[] s2plays = s2.getSongPlays();
		
		for (int i = 0; i < s1plays.length; i++) {
			sumOfProducts += (s1plays[i] * s2plays[i]);
			sumOfSquares1 += (s1plays[i] * s1plays[i]);
			sumOfSquares2 += (s2plays[i] * s2plays[i]);
		}
		
		//return 0 if one of the stations has no songs. This is signified by sum of squares of plays being 0
		if (sumOfSquares1 == 0 || sumOfSquares2 == 0) {
			return 0;
		}
		
		return sumOfProducts / (Math.sqrt(sumOfSquares1) * Math.sqrt(sumOfSquares2));
	}

	/**
	 * Gets the song with the highest recommendation for the given station that isn't 
	 * already played by the station.
	 * @param radioID ID of the station for which we want a recommendation.
	 * @return -1 if the given radioID is invalid
	 *         -2 if there are not enough stations to make a recommendation
	 *         -3 if there are not enough songs to make a recommendation
     *          otherwise the ID of the most highly recommended song for this station
	 */
	public int bestRecommendation(int radioID) {
		if (radioID < 0 || radioID > stationArray.length-1) {
			return -1;
		}
		if (stationArray.length == 1) {
			return -2;
		}
		//check if all songs are played on the station. If so, we can't make a recommendation.
		int songChecker = 0;
		Station s = stationArray[radioID];
		while (songChecker < songArray.length) {
			if (s.getSongPlays()[songChecker] == 0) {
				break;
			}
			songChecker++;
		}
		if (songChecker == songArray.length) {
			return -3; //if we reach the end of the array and all songs have been played at least once, return -3 
		}
		
		//start at songChecker, because wherever songChecker is will be the first song that is not played on the station
		int recommendedSong = songChecker;
		//iterate over all songs. If makeRecommendation(recommendedSong) > makeRecommendation(songChecker), update recommendedSong
		while (songChecker < songArray.length) {
			if (s.getSongPlays()[songChecker] == 0 && 
					makeRecommendation(songChecker, radioID) > makeRecommendation(recommendedSong, radioID)) {
				recommendedSong = songChecker; //only update recommendedSong if it has not been played and is a better recommendation
			}
			songChecker++;
		}
		return recommendedSong;
	}

	/**
	 * Computes the recommendation of a given song to a particular radio station
	 * @param recSongID Recommended song ID
	 * @param radioID ID of station being recommended to
	 * @return Value indicating how highly recommended is the song
	 * @return -1 if the given radioID is invalid
	 *         -2 if the given recSongID is invalid
     *          otherwise a value indicating how highly recommended is the song
	 */
	public double makeRecommendation(int recSongID, int radioID) {
		if (radioID < 0 || radioID > stationArray.length-1) {
			return -1;
		}
		if (recSongID < 0 || recSongID > songArray.length-1) {
			return -2;
		}
		//first compute average plays for this station, and use it to initialize returned value
		double recommendationValue = averageSongPlays(radioID);
		
		//compute total similarity to this station
		double totalSimilarity = totalStationSimilarity(radioID);
		
		/*if totalSimilarity is zero, it means each individual similarity is zero, and we can
		 * immediately return recommendationValue 
		 */
		if (totalSimilarity == 0) {
			return recommendationValue;
		}
		
		/* Iterate through all stations and add weighted average of plays of song recSongID. 
		 * Formula is product[numberOfPlays(currentStation) - averagePlays(currentStation), stationSimilarity to radioID] 
		 * all divided by totalSimilarity. 
		 */
		for (int i = 0; i < stationArray.length; i++) {
			if (i != radioID && numberOfTimesPlayed(recSongID, i) > 0) { //only consider station i if it has played the song
				recommendationValue = recommendationValue + ((numberOfTimesPlayed(recSongID, i) - averageSongPlays(i)) * 
						stationSimilarity(stationArray[radioID], stationArray[i]) / totalSimilarity); 
			}
		}
		
		return recommendationValue;
	}
	
	/**
	 * Computes sum of similarities of all stations to station s
	 * Helper method for makeRecommendation
	 * @param s Index of the station to compare around
	 * @return Value indicating sum of all station similarity values
	 */
	public double totalStationSimilarity(int s) {
		double totalSimilarity = 0;
		for (int i = 0; i<stationArray.length; i++) {
			if (i != s) {
				totalSimilarity += stationSimilarity(stationArray[s], stationArray[i]);
			}
		}
		return totalSimilarity;
	}
	
	/**Compute the ratio of similarity of s1 to s2 over sum of similarities to s2
	 * 
	 * @param s1 station to compare to s2
	 * @param s2 station to compare to all stations
	 * @return value corresponding to ratio of similarities
	 */
	public double similarityRatio(int s1, int s2) {
		double totalSim = totalStationSimilarity(s2);
		if (totalSim != 0) {
			return stationSimilarity(stationArray[s1], stationArray[s2]) / totalSim;
		}
		else return 0;
	}
	
	/**
	 * Computes average song plays on a station, i.e. if a song is played on a station, how
	 * many times on average is it played? Helper method for makeRecommendation
	 * playlistLength divided by number of unique songs on the playlist 
	 * @param s Index of station to compute average play of song
	 * @return value of average song plays on a station
	 */
	public double averageSongPlays(int s) {
		//take care of the case that the station's playlist is empty
		if (stationArray[s].getPlaylistLength()==0) {
			return 0;
		}
		//first, compute number of unique songs for the station using its songPlays
		double uniqueSongs = 0; 
		for (int i = 0; i < songArray.length; i++) {
			if (stationArray[s].getSongPlays()[i] > 0) {
				uniqueSongs++; //if a song is played on the station, regardless of number of times, increment uniqueSongs
			}
		}
		return (((double) stationArray[s].getPlaylistLength()) / uniqueSongs);
	}
	
	/**
	 * Return number of times a specific song is played on a station
	 * @param songID Song to check number of plays for
	 * @param stationID Station to check 
	 * @return
	 */
	public double numberOfTimesPlayed(int songID, int stationID) {
		return stationArray[stationID].getSongPlays()[songID];
	}
	
	/**
	 * Returns array of all songs ordered by index
	 * @return Array of all songs
	 */
	public Song[] getSongs() {
		return songArray;
	}
	
	/**
	 * Returns array of all stations ordered by index
	 * @return Array of all stations
	 */
	public Station[] getStations() {
		return stationArray;
	}

}
