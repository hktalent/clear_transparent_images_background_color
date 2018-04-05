package com.mtx.hktalent;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
/*
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

OutputStream outStream = new FileOutputStream("outFile.jpg");
JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outStream);

InputStream backGroundImage = new FileInputStream(new File(args[0]));
JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(backGroundImage);
BufferedImage imageBack = new BufferedImage(40,30,BufferedImage.
TYPE_INT_RGB);
// Define foreground image -- will be partially transparent.
InputStream foreGroundImage = new FileInputStream(new File(args[1]));
JPEGImageDecoder decoder2 = JPEGCodec.createJPEGDecoder(foreGroundImage);
BufferedImage imageFore = new BufferedImage(40,30,BufferedImage.
TYPE_INT_ARGB);
*/
public class Clear_transparent_images_background_color
{
    // 操作图像的对象
    protected BufferedImage bimg = null;
    // 宽高
    protected int width = 0, height = 0;
    String szFileName,toDir,szCurDir;

    protected String szNewFileName = null;

    // 保留的颜色:黑色、红色、绿色
    int []blColor = new int[]{0x000000,0xff0000,0x32cd32};

    // 根据保留颜色，返回颜色
    public int getColor(int n)
    {
        int nC = 0;
        for(int i = 0; i < blColor.length; i++)
        {
            nC = nC | (n & blColor[i]);
        }
        return nC;
    }

    public Clear_transparent_images_background_color(String szFileName, String toDir,String szCurDir)
    {
        this.szFileName = szFileName;
        this.toDir = toDir;
        this.szCurDir = szCurDir;

        // 将文件存储到其他目录
        // 文件名位置
        int n = szFileName.lastIndexOf(File.pathSeparator);
        Strring s = szFileName.substring(szCurDir.length()), 
        // 文件名
        s1 = szFileName.substring(n);
        // 新的文件目录
        s = toDir + File.pathSeparator + s.substring(0, n);
        new File(s).mkdirs();
        szNewFileName = s + s1;
        if(new File(szNewFileName).exists())
        {
            System.out.println("文件" + szNewFileName + "已经存在，跳过处理");
        }
        else
        {
            readFile(szFileName);
            clearBg();
            writeNewFile();
        }
    }

    // 1、读取文件
    public void readFile(Strings szFileName)
    {
        try {
            bimg = ImageIO.read(new File(szFileName));
            width = bimg.getWidth();
            height = bimg.getHeight();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    protected long []cntColor = new long[255];
    // 2、统计背景色
    public void getBackGroud()
    {
        if(null == bimg || 0 == width || 0 == height)return;
        for(int i = 0; i < 255; i++)
            cntColor[i] = 0;
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                cntColor[bimg.getRGB(x, y)]++;
            }
        }
    }
    // 3、去除背景
    public void clearBg()
    {
        int w = width, h = height, k = 0;
        int[] temp = new int[9];
        for(int x = 1; x < w; x++)
        {
            for(int y = 1; y < h; y++)
            {
                bimg.setRGB(x, y, getColor(bimg.getRGB(x, y)));
            }
        }
        // 4、滤波
        midImg();
    }

    // 4、滤波
    public void midImg()
    {
        int w = width - 1, h = height - 1, k = 0;
        int[] temp = new int[9];
        for(int x = 1; x < w; x++)
        {
            for(int y = 1; y < h; y++)
            {
                temp[k++] = bimg.getRGB(x - 1, y - 1);
                temp[k++] = bimg.getRGB(x, y - 1);
                temp[k++] = bimg.getRGB(x + 1, y - 1);
                temp[k++] = bimg.getRGB(x - 1, y);
                temp[k++] = bimg.getRGB(x, y);
                temp[k++] = bimg.getRGB(x + 1, y);
                temp[k++] = bimg.getRGB(x - 1, y + 1);
                temp[k++] = bimg.getRGB(x, y + 1);
                temp[k++] = bimg.getRGB(x + 1, y + 1);
                // 获取中值
                Arrays.sort(temp);
                bimg.setRGB(x, y, temp[4]);
            }
        }
    }
    
    // 6、保存文件
    public void writeNewFile()
    {
        BufferedImage bufferedImage = new BufferedImage(bimg.getWidth(null), bimg.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g2 = bufferedImage.createGraphics();
        ///设置背景色///
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bimg.getWidth(), bimg.getHeight());

        // 设置水印
        // g.setColor(Color.BLACK);
        // g.drawString(20, 20, "Hello");

        g2.drawImage(bimg, 0, 0, null);
        g2.dispose();
       
        ImageIO.write(bufferedImage, "PNG", new File(szNewFileName));
    }
    
}