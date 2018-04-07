package com.mtx.hktalent;
import java.io.Console;
import java.io.File;
import java.lang.*;
import org.apache.commons.cli.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/*
cd "~/safe/clear_transparent_images_background_color/com/mtx/hktalent/" && javac Main.java && java Main
*/
public class Main
{
    public Main(){}

    public static Pattern p = null;
    public static String inputFilePath;
    public static String outputFilePath;
    public void doFile(String s)
    {
        Matcher m = p.matcher(s);
        if(m.find())
        {
            new Clear_transparent_images_background_color(s, outputFilePath, inputFilePath);
        }
    }
    // 5、读取、遍历目录
    public void listFilesAndFolders(String directoryName)
    {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList)
        {
            if(!file.exists())continue;
            if(file.isFile())doFile(file.getAbsolutePath());
            else
            {
                listFilesAndFolders(file.getAbsolutePath());
            }
        }
    }

    // 7、命令行解析
    public static void main(String[] args)
    {
        Options options = new Options();

        Option input = new Option("i", "iDir", true, "input file path，要处理的图片目录");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "oDir", true, "output file path，输出的新目录");
        output.setRequired(true);
        options.addOption(output);

        Option filter = new Option("f", "filter", true, "filter file \\.(jpg|jpeg|png|gif)$，正则表达式");
        filter.setRequired(false);
        options.addOption(filter);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e)
        {
            System.out.println(e.getMessage());
            formatter.printHelp("欢迎使用巅狼团队提供的工具\nclear_transparent_images_background_color", options);
            System.exit(1);
            return;
        }
        try {
            // inputFilePath = "/Volumes/winxp/eclipse";// cmd.getOptionValue("iDir");
            // outputFilePath = ".";//cmd.getOptionValue("oDir");
            // String szFilter = null;//cmd.getOptionValue("filter");

            inputFilePath = cmd.getOptionValue("iDir");
            outputFilePath = cmd.getOptionValue("oDir");
            String szFilter = cmd.getOptionValue("filter");
            if(null == szFilter)
                szFilter = "\\.(jpg|jpeg|png|gif)$";
            p = Pattern.compile(szFilter, Pattern.CASE_INSENSITIVE);
            Main m = new Main();
            m.listFilesAndFolders(inputFilePath);
            /*
            Files.list(Paths.get(inputFilePath))
            .filter(Files::isRegularFile)
            .forEach(System.out::println);
            */
        } catch (Exception e)
        {
            e.printStackTrace();;
        }
        
        System.out.println("ok");
    }
}
