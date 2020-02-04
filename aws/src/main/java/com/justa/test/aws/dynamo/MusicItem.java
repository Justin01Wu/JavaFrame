package com.justa.test.aws.dynamo;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

// https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.OptimisticLocking.html
@DynamoDBTable(tableName="Music")
public class MusicItem {	
	
	private String artist;
	private String songTitle;
	private String albumTitle;
	private String genre;
	private List<String> authors;
	private Double criticRating;
	private Double price;
	private Integer year;
	private String useless;
	private Long version;
	
	// hash key is mandatory
	@DynamoDBHashKey(attributeName="Artist")  
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	// range key is optional, it means hashkey itself itself is unique if we don't have range key  
	@DynamoDBRangeKey(attributeName="SongTitle")
	public String getSongTitle() {
		return songTitle;
	}
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	
	@DynamoDBAttribute(attributeName="AlbumTitle")  
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	
	@DynamoDBAttribute(attributeName = "Authors")
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	@DynamoDBAttribute(attributeName = "CriticRating")
	public Double getCriticRating() {
		return criticRating;
	}
	public void setCriticRating(Double criticRating) {
		this.criticRating = criticRating;
	}
	
	@DynamoDBAttribute(attributeName = "Year")
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@DynamoDBIgnore
	public String getUseless() {
		return useless;
	}
	public void setUseless(String useless) {
		this.useless = useless;
	}
	
	@DynamoDBVersionAttribute
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	@DynamoDBAttribute(attributeName = "Price")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@DynamoDBAttribute(attributeName = "Genre")
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	

}
