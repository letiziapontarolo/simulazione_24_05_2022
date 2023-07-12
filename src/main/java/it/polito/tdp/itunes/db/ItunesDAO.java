package it.polito.tdp.itunes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Arco;
import it.polito.tdp.itunes.model.Artist;
import it.polito.tdp.itunes.model.Genre;
import it.polito.tdp.itunes.model.MediaType;
import it.polito.tdp.itunes.model.Playlist;
import it.polito.tdp.itunes.model.Track;

public class ItunesDAO {
	
	public List<String> listaGeneri() {
		
		final String sql = "SELECT distinct genre.Name "
				+ "FROM genre "
				+ "ORDER BY genre.Name";
		List<String> result = new ArrayList<String>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Name"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;	
	}
	
     public void creaVertici(Map<Integer, Track> trackIdMap, String genere) {
		
		final String sql = "SELECT track.* "
				+ "FROM track, genre "
				+ "WHERE track.GenreId = genre.GenreId AND genre.Name = (?) "
				+ "GROUP BY track.TrackId";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Track t = new Track(res.getInt("TrackId"), 
						res.getString("Name"), 
						res.getString("Composer"), 
						res.getInt("Milliseconds"), 
						res.getInt("Bytes"),
						res.getDouble("UnitPrice"));
						
				trackIdMap.put(res.getInt("TrackId"), t);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");

	}
     }
     
     public List<Arco> listaArchi(Map<Integer, Track> trackIdMap, String genere) {
    	 
    	 final String sql = "SELECT t1.TrackId AS track1, t2.TrackId AS track2, ABS(t1.Milliseconds - t2.Milliseconds) AS peso "
    	 		+ "FROM "
    	 		+ "(SELECT track.* "
    	 		+ "FROM track, genre "
    	 		+ "WHERE track.GenreId = genre.GenreId AND genre.Name = (?) "
    	 		+ "GROUP BY track.TrackId) t1, "
    	 		+ "(SELECT track.* "
    	 		+ "FROM track, genre "
    	 		+ "WHERE track.GenreId = genre.GenreId AND genre.Name = (?) "
    	 		+ "GROUP BY track.TrackId) t2 "
    	 		+ "WHERE t1.TrackId > t2.TrackId AND t1.MediaTypeId = t2.MediaTypeId";
 		List<Arco> result = new ArrayList<Arco>();
 		
 		try {
 			Connection conn = DBConnect.getConnection();
 			PreparedStatement st = conn.prepareStatement(sql);
 			st.setString(1, genere);
 			st.setString(2, genere);
 			ResultSet res = st.executeQuery();

 			while (res.next()) {
 				
 				Track t1 = trackIdMap.get(res.getInt("track1"));
 				Track t2 = trackIdMap.get(res.getInt("track2"));
 				
 				Arco a = new Arco(t1, t2, res.getInt("peso"));
 				result.add(a);
 				
 				
 			}
 			conn.close();
 		} catch (SQLException e) {
 			e.printStackTrace();
 			throw new RuntimeException("SQL Error");
 		}
 		return result;	
    	 
    	 
     }
	
	public List<Album> getAllAlbums(){
		final String sql = "SELECT * FROM Album";
		List<Album> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Album(res.getInt("AlbumId"), res.getString("Title")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Artist> getAllArtists(){
		final String sql = "SELECT * FROM Artist";
		List<Artist> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Artist(res.getInt("ArtistId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Playlist> getAllPlaylists(){
		final String sql = "SELECT * FROM Playlist";
		List<Playlist> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Playlist(res.getInt("PlaylistId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Track> getAllTracks(){
		final String sql = "SELECT * FROM Track";
		List<Track> result = new ArrayList<Track>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Track(res.getInt("TrackId"), res.getString("Name"), 
						res.getString("Composer"), res.getInt("Milliseconds"), 
						res.getInt("Bytes"),res.getDouble("UnitPrice")));
			
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Genre> getAllGenres(){
		final String sql = "SELECT * FROM Genre";
		List<Genre> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Genre(res.getInt("GenreId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<MediaType> getAllMediaTypes(){
		final String sql = "SELECT * FROM MediaType";
		List<MediaType> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new MediaType(res.getInt("MediaTypeId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}

	
	
}
