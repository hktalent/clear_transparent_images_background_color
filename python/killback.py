import pgmagick

def remove_background(image, background=None):
    """Returns a copy of `image` that only contains the parts that is distinct
       from the background. If background is None, returns parts that are
       distinct from white."""
    if background is None:
        background = pgmagick.Image(image.size(), 'white')
    elif isinstance(background, pgmagick.Image):
        blob = pgmagick.Blob()
        background.write(blob)
        background = pgmagick.Image(blob, image.size())
    else:
        background = pgmagick.Image(image.size(), background)
    background.composite(image, 0, 0, pgmagick.CompositeOperator.DifferenceCompositeOp)
    background.threshold(25)
    blob = pgmagick.Blob()
    image.write(blob)
    image = pgmagick.Image(blob, image.size())
    image.composite(background, 0, 0, pgmagick.CompositeOperator.CopyOpacityCompositeOp)
    return image

def main():
    import argparse
    import os

    parser = argparse.ArgumentParser(description='''
        Removes the background from an image.
        Background can be a colour, an image, or plain white.''')
    parser.add_argument('image', help='Image to remove background from.')
    parser.add_argument('save', help='Image to save result to.', nargs='?')
    group = parser.add_mutually_exclusive_group()
    group.add_argument('-b', '--background', help='Background image.')
    group.add_argument('-c', '--colour', help='Colour of the canvas to use '
                       'as background.')
    parser.add_argument('-q', '--quiet', help='No message printed.',
                        action='store_true')
    args = parser.parse_args()
    if args.background is not None:
        background = pgmagick.Image(args.background)
    elif args.colour is not None:
        background = args.colour
    else:
        background = 'white'
    if args.save is None:
        save = '%s_transparent.png' % os.path.splitext(args.image)[0]
    else:
        save = args.save
    if not args.quiet:
        print 'Removing background from: %s' % args.image
        if args.background is not None:
            print 'Based on image:', args.background
        else:
            print 'Based on canvas:', background
        print 'Saving to:', save
    image = pgmagick.Image(args.image)
    final = remove_background(image, background)
    final.write(save)

if __name__ == '__main__':
    main()

