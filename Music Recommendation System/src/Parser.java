import java.io.*;
import java.util.Scanner;

public class Parser {	
	
	private Station[] stationArray; //array of all stations
	private Song[] songArray; //array of all songs
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Parser x = new Parser("songs.txt", "stations.txt", "playlist.txt");
		for (int i = 0; i < x.songArray.length; i++) {
			System.out.println(x.songArray[i].getStatistics()[1]);
		}
	}
	
	/**Parses the song, station and playlist files and builds the songs and stations array
	 * with information provided in the files
	 * @param songFile The name of the songs dataset file
	 * @param stationFile The name of the station dataset file
	 * @param playlistsFile The name of the playlist dataset file
	 * @throws FileNotFoundException 
	 */
	public Parser(String songFile, String stationFile, String playlistsFile) throws FileNotFoundException{
		//build station array by reading stationFile
		Scanner stationReader = new Scanner(new File(stationFile)); 
		int numberOfStations = Integer.parseInt(stationReader.nextLine());
		stationArray = new Station[numberOfStations];
		int stationCounter = 0;
		while (stationCounter < numberOfStations) {
			String stationLine = stationReader.nextLine();
			stationArray[stationCounter] = parseOneStation(stationLine);
			stationCounter++;
		}
		stationReader.close();
		
		//build song array by reading songFile
		Scanner songReader = new Scanner(new File(songFile));
		int numberOfSongs = Integer.parseInt(songReader.nextLine());
		songArray = new Song[numberOfSongs];
		int songCounter = 0;
		while (songCounter < numberOfSongs) {
			String songLine = songReader.nextLine();
			songArray[songCounter] = parseOneSong(songLine, stationArray.length);
			songCounter++;
		}
		songReader.close();
		
		/* Using the playlist file, build songPlays array for each station describing how many times it plays each song, and 
		 * stationPlays array for each song describing how many times that song is played on each station
		 */
		Scanner playlistReader = new Scanner(new File(playlistsFile));
		while (playlistReader.hasNextLine()) {
			String playlistLine = playlistReader.nextLine();
			String[] stationAndSongs = playlistLine.split(";"); //string array representation of the playlist line
			int station = Integer.parseInt(stationAndSongs[0]); //the station we are dealing with in this iteration
			int[] songPlays = new int[songArray.length]; //will store the number of times that the station plays each song in each Station object
			for (int i = 1; i < stationAndSongs.length; i++) {
				songArray[Integer.parseInt(stationAndSongs[i])].incrementStationPlay(station); //increment song i's number of plays for the station in the Song objects
				songPlays[Integer.parseInt(stationAndSongs[i])]++; //increment song i's number of plays for the station in the Station object
			}
			stationArray[station].setPlaylistLength(stationAndSongs.length-1); //each station's playlist length is the length of the playlist line minus one (one of the numbers represents the station itself)
			stationArray[station].setSongPlays(songPlays);
		}
		playlistReader.close();
	}
	/**Each line of the dataset represents a distinct radio station. 
	 * This method should parse and construct a station from a line of the file.
	 * 
	 * @param line A line of your dataset file.
	 * @return A station that is described by line.
	 */
	public Station parseOneStation(String line){
		String[] IDAndName = line.split(";");
		int ID = Integer.parseInt(IDAndName[0]);
		String name = IDAndName[1];
		return new Station(name, ID);
	}
	/**Each line of the dataset represents a distinct song.
	 * This method should parse and construct a song from a line of the file.
	 * @param line  A line of your dataset file.
	 * @param numberOfStations The total number of radio stations.
	 * @return  A song that is described by line.
	 */
	public Song parseOneSong(String line, int numberOfStations){
		String[] IDNameAndArtist = line.split(";");
		int ID = Integer.parseInt(IDNameAndArtist[0]);
		String name = IDNameAndArtist[1];
		String artist = IDNameAndArtist[2];
		return new Song(name, artist, ID, numberOfStations);
	}
	/**
	 * @return An array of song objects made from the songs dataset file. 
	 * Elements are indexed by song id. For example, a song with 
	 * song id 14853 belongs in Song[14853]. This works because the Song Ids
	 * will be zero-based indexed
	 */
	public Song[] getSongs() {
		return songArray;
	}
	/**
	 * @return An array of station objects made from the stations dataset file. 
	 * Elements are indexed by station id. For example, a station with 
	 * station id 562 belongs in Song[562]. This works because the Song Ids
	 * will be zero-based indexed
	 */
	public Station[] getStations() {
		return stationArray;
	}
	
}
