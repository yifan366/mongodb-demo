package com.zhongtai.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import com.zhongtai.modle.UserGroup;
import com.zhongtai.service.IUserServie;
import com.zhongtai.tools.Conf;
import com.zhongtai.utils.HttpRequest;

@Service("userServie")
public class UserServieImpl implements IUserServie {

	//private String httpurl = "http://120.76.223.218:8080/manaplatform/mobileTerminal_http/";
	private String httpurl = Conf.mongodb.httpserver.url;

	@Override
	public Integer validateUser(String username, String password) {
		// TODO Auto-generated method stub
		Integer userid = null;

		String result = null;
		try {
			result = HttpRequest.sendPost(httpurl + "validateLoginNamePwd",
					"loginName=" + username + "&loginPwd=" + password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	//	System.out.println(result);
		
		JSONTokener jt = new JSONTokener(result);

		JSONObject jo = (JSONObject) jt.nextValue();

		if (jo.getString("flag").equals("1") || jo.getString("flag").equals("0")) {
			userid = jo.getInt("userid");
		}

		return userid;
	}

	@Override
	public List<UserGroup> listGroup(Integer userid) {
		// TODO Auto-generated method stub
		List<UserGroup> resultlist = new ArrayList<UserGroup>();

		String result = null;
		try {
			result = HttpRequest.sendPost(httpurl + "selectFamilyGroup", "userId=" + userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONTokener jt = new JSONTokener(result);

		JSONObject jo = (JSONObject) jt.nextValue();

		JSONArray jsonArray = jo.getJSONArray("data");

		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject json = (JSONObject) jsonArray.get(i);

			UserGroup ug = new UserGroup();
			ug.setGroupid(json.getInt("groupId"));
			ug.setGroupname(json.getString("groupname"));

			resultlist.add(ug);
		}

		return resultlist;
	}

	@Override
	public List<Integer> listUserId(Integer groupid) {

		List<Integer> list = new ArrayList<Integer>();
		try {
			String result = HttpRequest.sendPost(httpurl + "selectFamilyGroupMember", "groupId=" + groupid);

			JSONTokener jt = new JSONTokener(result);

			JSONObject jo = (JSONObject) jt.nextValue();

			JSONArray jsonArray = jo.getJSONArray("data");

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject json = (JSONObject) jsonArray.get(i);

				list.add(json.getInt("userid"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Integer> listUseridByUserid(Integer userid) {
		// TODO Auto-generated method stub

		List<Integer> alllist = new ArrayList<Integer>();

		List<UserGroup> listgroup = listGroup(userid);

		for (UserGroup userGroup : listgroup) {
			Integer groupid = userGroup.getGroupid();

			alllist.addAll(listUserId(groupid));
		}
		
		if (listgroup.size()==0) {
			alllist.add(userid);
		}

		HashSet h = new HashSet(alllist);
		alllist.clear();
		alllist.addAll(h);

		return alllist;
	}
	
	/**
	 * 根据用户id查询对应组id
	 */
	public Integer getGroupid(Integer userid){
		
		List<UserGroup> list = listGroup(userid);
		if (list.size()>0) {
			return list.get(0).getGroupid();
		}
		return null;
	}
	
	/**
	 * 根据用户名密码查询对应组id
	 */
	public Integer getGroupid(String username,String pwd){
		
		Integer groupid = null;
		try {
			Integer userid = validateUser(username, pwd);
			if (userid !=null) {
				List<UserGroup> list = listGroup(userid);
				if (list.size()>0) {
					groupid = list.get(0).getGroupid();
				}
			}
			
		} catch (Exception e) {
		}
		return groupid;
	}

}
