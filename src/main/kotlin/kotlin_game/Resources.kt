package kotlin_game

import java.awt.Color
import java.awt.Image
import java.awt.Toolkit
import java.awt.image.FilteredImageSource
import java.awt.image.RGBImageFilter
import javax.swing.ImageIcon


class Resources {
    companion object {
        val images: HashMap<String, Image> by lazy { loadImages() }

        fun getImage(imgName: String) = images[imgName]!!


        //-------------------------------------------------//
        //                  Private Functions              //
        //-------------------------------------------------//

        private fun loadImages(): HashMap<String, Image> {
            val imgs = HashMap<String, Image>()
            for (i in 1..7) {
                imgs.put("blood$i", getTransparentImage(ImageIcon("res/blood/bloodsplats_000$i.png")))
            }
            imgs.put("smallBlood", getTransparentImage(ImageIcon("res/blood/smallBlood.png")))

            imgs.put("actorImage", getTransparentImage(ImageIcon("res/test.png")))

            return imgs
        }

        private fun getTransparentImage(img: ImageIcon): Image {
            val filter = object : RGBImageFilter() {
                internal var transparentColor = Color.white.rgb or 0xFF000000.toInt()

                override fun filterRGB(x: Int, y: Int, rgb: Int): Int {
                    if (rgb or 0xFF000000.toInt() == transparentColor) {
                        return 0x00FFFFFF and rgb
                    } else {
                        return rgb
                    }
                }
            }

            val filteredImgProd = FilteredImageSource(img.image.source, filter)
            val transparentImg = Toolkit.getDefaultToolkit().createImage(filteredImgProd)

            return transparentImg
        }
    }
}