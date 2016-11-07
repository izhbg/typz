package com.izhbg.typz.im.store.response.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


public class TShStoreAttachefile implements Serializable {

	private String id;

	
	public TShStoreAttachefile() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}