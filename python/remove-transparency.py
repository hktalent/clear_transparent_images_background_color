# pip2 install pillow
import os
from PIL import Image

# Absolute path to this script
scriptdir = 'test1'#os.path.dirname(os.path.abspath(__file__))

# Walk through directory
for root, subfolders, files in os.walk(scriptdir):
    for file in files:
        try:
            szFileName = os.path.join('./', root, file)
            
            image = Image.open(szFileName)
            image = image.convert("RGBA")
            print szFileName + ": " + image.mode
            # If image has an alpha channel
            print 
            if image.mode == 'RGBA':
                # Create a blank background image
                bg = Image.new('RGB', image.size, (255, 255, 255))
                # Paste image to background image
                bg.paste(image, (0, 0), image)
                # Save pasted image as image
                nFile = os.path.join('./', root, file)
		print nFile
                bg.save(nFile, "PNG")

        except Exception as e:
            print(e)
            pass

