package com.yazilimokulu.mvc;

public class HitCounter {

	private int hits;

	
	public HitCounter() {
		System.out.println("Hit Counter Instantiated");
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
	
	
}
