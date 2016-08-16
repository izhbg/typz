package com.izhbg.typz.shop.goods.dto;

import java.io.Serializable;

public class ImageCutDto implements Serializable {

  private static final long serialVersionUID = 2932865922241986034L;

  /**
   * 文件上传时，文件的ID
   */
  private String fileId;

  /**
   * 图片X坐标点
   */
  private Double imageX;

  /**
   * 图片Y坐标点
   */
  private Double imageY;

  /**
   * 压缩后图片宽度
   */
  private Double imageW;

  /**
   * 压缩后图片高度
   */
  private Double imageH;

  /**
   * 图片缩放比例
   */
  private Double imageScale;

  /**
   * 剪切框X坐标点
   */
  private Double selectorX;

  /**
   * 剪切框Y坐标点
   */
  private Double selectorY;

  /**
   * 选择框宽度
   */
  private Double selectorW;

  /**
   * 选择框高度
   */
  private Double selectorH;

  public Double getSelectorW() {
    return selectorW;
  }

  public void setSelectorW(Double selectorW) {
    this.selectorW = selectorW;
  }

  public Double getSelectorH() {
    return selectorH;
  }

  public void setSelectorH(Double selectorH) {
    this.selectorH = selectorH;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public Double getImageScale() {
    return imageScale;
  }

  public void setImageScale(Double imageScale) {
    this.imageScale = imageScale;
  }

  public Double getImageX() {
    return imageX;
  }

  public void setImageX(Double imageX) {
    this.imageX = imageX;
  }

  public Double getImageY() {
    return imageY;
  }

  public void setImageY(Double imageY) {
    this.imageY = imageY;
  }

  public Double getImageW() {
    return imageW;
  }

  public void setImageW(Double imageW) {
    this.imageW = imageW;
  }

  public Double getImageH() {
    return imageH;
  }

  public void setImageH(Double imageH) {
    this.imageH = imageH;
  }

  public Double getSelectorX() {
    return selectorX;
  }

  public void setSelectorX(Double selectorX) {
    this.selectorX = selectorX;
  }

  public Double getSelectorY() {
    return selectorY;
  }

  public void setSelectorY(Double selectorY) {
    this.selectorY = selectorY;
  }

  @Override public String toString() {
    return "ImageCutDto{" +
        "fileId='" + fileId + '\'' +
        ", imageX=" + imageX +
        ", imageY=" + imageY +
        ", imageW=" + imageW +
        ", imageH=" + imageH +
        ", imageScale=" + imageScale +
        ", selectorX=" + selectorX +
        ", selectorY=" + selectorY +
        ", selectorW=" + selectorW +
        ", selectorH=" + selectorH +
        '}';
  }
}
