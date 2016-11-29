package com.gighub.app.model;

/**
 * Created by user on 30/11/2016.
 */

public class CloudinaryResponse{
    private String[] tags;

    private String bytes;

    private String etag;

    private String width;

    private String public_id;

    private String format;

    private String type;

    private String url;

    private String original_filename;

    private String version;

    private String height;

    private String resource_type;

    private String created_at;

    private String signature;

    private String secure_url;

    public String[] getTags ()
    {
        return tags;
    }

    public void setTags (String[] tags)
    {
        this.tags = tags;
    }

    public String getBytes ()
    {
        return bytes;
    }

    public void setBytes (String bytes)
    {
        this.bytes = bytes;
    }

    public String getEtag ()
    {
        return etag;
    }

    public void setEtag (String etag)
    {
        this.etag = etag;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getPublic_id ()
    {
        return public_id;
    }

    public void setPublic_id (String public_id)
    {
        this.public_id = public_id;
    }

    public String getFormat ()
    {
        return format;
    }

    public void setFormat (String format)
    {
        this.format = format;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getOriginal_filename ()
    {
        return original_filename;
    }

    public void setOriginal_filename (String original_filename)
    {
        this.original_filename = original_filename;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getResource_type ()
    {
        return resource_type;
    }

    public void setResource_type (String resource_type)
    {
        this.resource_type = resource_type;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getSignature ()
    {
        return signature;
    }

    public void setSignature (String signature)
    {
        this.signature = signature;
    }

    public String getSecure_url ()
    {
        return secure_url;
    }

    public void setSecure_url (String secure_url)
    {
        this.secure_url = secure_url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tags = "+tags+", bytes = "+bytes+", etag = "+etag+", width = "+width+", public_id = "+public_id+", format = "+format+", type = "+type+", url = "+url+", original_filename = "+original_filename+", version = "+version+", height = "+height+", resource_type = "+resource_type+", created_at = "+created_at+", signature = "+signature+", secure_url = "+secure_url+"]";
    }
}