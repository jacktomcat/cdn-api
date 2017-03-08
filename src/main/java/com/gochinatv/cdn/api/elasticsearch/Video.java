package com.gochinatv.cdn.api.elasticsearch;

import java.util.Date;

public class Video {

	private Long id;
	private Long albumId;
	private String src;
	private String name;
	private String subname;
	private String installments;
	private String duration;
	private String standardPic;
	private String description;
	private String tag;
	private Integer vedioType;// 1 剧集 2期数
	private Integer isdisplay;
	private Integer displayOrder;
	private Date createTime;
	private Date modifyTime;
	private String scanType;
	private Long createUserId;
	private Long lastModifyUserId;
	private Integer sort;
	private Date logoutTime;// 视频下线时间
	private String artificial;// 人工下线标记
	private String countsView;// 观看次数
	private String cnName;// 中文标题

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

	public String getInstallments() {
		return installments;
	}

	public void setInstallments(String installments) {
		this.installments = installments;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStandardPic() {
		return standardPic;
	}

	public void setStandardPic(String standardPic) {
		this.standardPic = standardPic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getVedioType() {
		return vedioType;
	}

	public void setVedioType(Integer vedioType) {
		this.vedioType = vedioType;
	}

	public Integer getIsdisplay() {
		return isdisplay;
	}

	public void setIsdisplay(Integer isdisplay) {
		this.isdisplay = isdisplay;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(Long lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getArtificial() {
		return artificial;
	}

	public void setArtificial(String artificial) {
		this.artificial = artificial;
	}

	public String getCountsView() {
		return countsView;
	}

	public void setCountsView(String countsView) {
		this.countsView = countsView;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

}
