package com.niupiao.niupiao.models;

/**
 * The Class Data is a simple Java Bean that is used to hold Name, Detail and
 * image pairs.
 */
public class Data
{

    /** The title1. */
    private String title;

    /** The description. */
    private String desc;

    /** The image resource id. */
    private int image;

    /**
     * Instantiates a new data.
     *
     * @param title
     *            the title1
     * @param desc
     *            the desc
     * @param image
     *            the image1
     */
    public Data(String title, String desc, int image)
    {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    /**
     * Instantiates a new data.
     *
     * @param title
     *            the title
     * @param image
     *            the image
     */
    public Data(String title, int image)
    {
        this.title = title;
        this.image = image;
    }

    /**
     * Gets the title1.
     *
     * @return the title1
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the title1.
     *
     * @param title1
     *            the new title1
     */
    public void setTitle(String title1)
    {
        this.title = title;
    }

    /**
     * Gets the desc.
     *
     * @return the desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * Sets the desc.
     *
     * @param desc
     *            the new desc
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * Gets the image1.
     *
     * @return the image1
     */
    public int getImage()
    {
        return image;
    }

    /**
     * Sets the image.
     *
     * @param image
     *            the new image1
     */
    public void setImage1(int image)
    {
        this.image = image;
    }

}
