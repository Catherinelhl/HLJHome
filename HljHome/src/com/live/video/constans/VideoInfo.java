package com.live.video.constans;

import java.io.Serializable;

public class VideoInfo implements Serializable {

	public String banben;
	public int id;
	public int img;
	public String logo;
	public String mark;
	public String title;
	public String zid;

	public String toString() {
		return "VideoInfo [id=" + this.id + ", mark=" + this.mark + ", title="
				+ this.title + ", img=" + this.img + ", banben=" + this.banben
				+ ", zid=" + this.zid + ", logo=" + this.logo + "]";
	}

}
