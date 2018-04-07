# clear_transparent_images_background_color
clear transparent images (jpg、png、ttf file) background color
批量清除图片文件背景颜色

# 批量处理命令
发现不用编程了
```
1、苹果系统：mac os系统

2、安装：brew
xcode-select --install
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
brew install ImageMagick


3、很不错的去除背景的命令、参数
1）、convert ok.jpg \( +clone -blur 0x20 \) -compose Divide_Src -composite  kkk.jpg
2）、convert ok.jpg -fuzz 20% -transparent white result.png

magicwand 1,1 -t 20 -f image -r outside -m overlay -o 0 image.jpg imgOutput.png
magick 1335624623-956109868.jpg -fuzz 20% -fill none -draw "alpha 1x1 floodfill" result.png


4、查找、批量处理
find ./ -type f \( -iname \*.jpg -o -iname \*.png -o -iname \*.ttf -o -iname \*.tif \) -exec convert {} \( +clone -blur 0x20 \) -compose Divide_Src -composite {} \;
```

