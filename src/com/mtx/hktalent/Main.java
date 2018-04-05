package com.mtx.hktalent;
import java.lang.*;
import org.apache.commons.cli.*;
/*
cd "~/safe/clear_transparent_images_background_color/com/mtx/hktalent/" && javac Main.java && java Main
*/
public class Main
{
    // 5、读取、遍历目录

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
        String inputFilePath = cmd.getOptionValue("iDir");
        String outputFilePath = cmd.getOptionValue("oDir");
        
        System.out.println("ok");
    }
}
