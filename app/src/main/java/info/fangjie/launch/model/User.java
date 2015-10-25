package info.fangjie.launch.model;

import info.fangjie.launch.R;

/**
 * Created by FangJie on 15/10/24.
 */
public class User {

    public  static final int MALE=1;
    public  static final int FEMALE=2;

    private String username;

    private String iconUrl;

    private int sex;

    private int score;

    private String[] address;


    public static String getUserPhoneByName(String name){
        if (name.equals("JayFang")){
            return "15927275948";
        }else if (name.equals("xiaoming")){
            return "18064091599";
        }else if (name.equals("")){
            return "13243253255";
        }else if (name.equals("")){
            return "13243235455";
        }

        return "18282838383";
    }

    public static int getUserSexByName(String name){
        if (name.equals("JayFang")){
            return MALE;
        }else if (name.equals("xiaoming")){
            return MALE;
        }else if (name.equals("Android")){
            return FEMALE;
        }else if (name.equals("eclipse")){
            return FEMALE;
        }

        return FEMALE;
    }

    public static int getUserIconByName(String name){
        if (name.equals("JayFang")){
            return R.drawable.icon;
        }else if (name.equals("xiaoming")){
            return R.drawable.ic_head_1;
        }else if (name.equals("Android")){
            return R.drawable.ic_head_4;
        }else if (name.equals("eclipse")){
            return R.drawable.ic_head_3;
        }
        return R.drawable.icon;
    }

}
