package com.stackroute.muzixmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Muzix {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="track_id")
	private String trackId;
	@Column(name="name")
	private String name;
	@Column(name="url")
	private String url;
	@Column(name="image_url")
	private String imageUrl;
	@Column(name="artist_name")
	private String artistName;
	@Column(name="user_id")
	private String userId;
	
	public Muzix(){}	
	
	public Muzix(String name, String url, String imageUrl, String artistName, String userId) {
		this.name = name;
		this.url = url;
		this.imageUrl = imageUrl;
		this.artistName = artistName;
		this.userId = userId;
	}
		
	public Muzix(int id, String trackId, String name, String url, String imageUrl, String artistName, String userId) {
		this.id = id;
		this.trackId = trackId;
		this.name = name;
		this.url = url;
		this.imageUrl = imageUrl;
		this.artistName = artistName;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
