/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.zftlive.android.library.imageloader.picture;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 图片数据模型，可以是网络的数据也可以是本地的数据
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class PictureBean implements Parcelable,Serializable {

  /**
   * 本地文件
   */
  public static int FILE_TYPE_SDCARD = 0;

  /**
   * Assets目录文件
   */
  public static int FILE_TYPE_ASSETS = 1;

  /**
   * drawable目录文件
   */
  public static int FILE_TYPE_DRAWABLE = 2;

  /**
   * content提供的文件
   */
  public static int FILE_TYPE_CONTENT = 3;

  /**
   * 网络文件
   */
  public static int FILE_TYPE_NETWORK = 4;

  /**
   * 
   */
  private static final long serialVersionUID = -3207914156256848202L;

  /**
   * 图片类型：默认网络图片<br>
   * 0-本地图片-->"file://mnt/sdcard/image.png"; <br>
   * 1-Assets目录图片-->"assets://image.png"<br>
   * 2-drawable目录下的图片-->"drawable://" + R.drawable.image; <br>
   * 3-内容提提供者中抓取图片-->"content://media/external/audio/albumart/13";<br>
   * 4-网络图片 -->"http://wwww.baidu.com/aaa.jpg";<br>
   */
  public int fileType = FILE_TYPE_NETWORK;

  /**
   * 图片文件名称
   */
  public String fileName = "";

  /**
   * 图片文件地址
   */
  public String filePath = "";

  public PictureBean() {
    this("");
  }

  public PictureBean(String filePath) {
    this("", filePath);
  }

  public PictureBean(String fileName, String filePath) {
    this(FILE_TYPE_NETWORK, fileName, filePath);
  }

  public PictureBean(int fileType, String fileName, String filePath) {
    this.fileType = fileType;
    this.fileName = fileName;
    this.filePath = filePath;
  }

  public int getFileType() {
    return fileType;
  }

  public void setFileType(int fileType) {
    this.fileType = fileType;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String toString() {
    return "PictureBean {fileType=" + fileType + ", fileName=" + fileName + ", filePath="
        + filePath + "}";
  }

  public static final Creator<PictureBean> CREATOR = new Creator<PictureBean>() {
    public PictureBean createFromParcel(Parcel source) {
      PictureBean mPicture = new PictureBean();
      mPicture.fileType = source.readInt();
      mPicture.fileName = source.readString();
      mPicture.filePath = source.readString();
      return mPicture;
    }

    public PictureBean[] newArray(int size) {
      return new PictureBean[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int arg1) {
    parcel.writeInt(fileType);
    parcel.writeString(fileName);
    parcel.writeString(filePath);
  }
}
