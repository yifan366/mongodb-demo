package com.zhongtai.modle;

import java.util.Comparator;

public class ComparatorFileInfo implements Comparator<VideosInfo> {
	 @Override
	    public int compare(VideosInfo f1, VideosInfo f2) {

		 	int flag = f1.getCurrentPart().compareTo(f2.getCurrentPart()) ;
	        
	        return flag;
	        
	    }
}
