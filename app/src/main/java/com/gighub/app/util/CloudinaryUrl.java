package com.gighub.app.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 27/10/2016.
 */
public class CloudinaryUrl {
    public Cloudinary cloudinary;
    public Map config;
    public void buildCloudinaryUrl(){
        config = new HashMap();
        config.put("cloud_name", "dv5anayv1");
        config.put("api_key", "627999879788938");
        config.put("api_secret", "Tfnf61ZtXn61Ct-Jbr_F__lzSJ8");
        cloudinary = new Cloudinary(config);
    }
}
