package project.smartlocker.services

import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.stereotype.Service
import project.smartlocker.http.models.hash.input.CreateHashRequest
import project.smartlocker.repository.HashRepository
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Service
class HashService(
    private val hashRepository: HashRepository
) {
    fun getHash(hash: String): QrScanDTO? {
        return hashRepository.getHash(hash)
    }

    fun createQrCode(input: CreateHashRequest){
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(input.hash, BarcodeFormat.QR_CODE, 300, 300)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        for (x in 0 until width) {
            for (y in 0 until height) {
                val color = if (bitMatrix.get(x, y)) 0x000000 else 0xFFFFFF
                image.setRGB(x, y, color)
            }
        }
        val baos = ByteArrayOutputStream()
        ImageIO.write(image, "png", baos)
        hashRepository.insertHash(input.hash, baos.toByteArray())
    }
}