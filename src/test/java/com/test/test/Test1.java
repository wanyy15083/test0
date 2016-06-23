package com.test.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import com.google.zxing.EncodeHintType;

public class Test1 {

	public static void main(String[] args) throws Exception {
		QRCode code = null;
		BufferedOutputStream bufo = null;
		for (int i = 0; i < 5; i++) {
			System.out.println(new Date());
			System.out.println(i + " start");
			String qrcodeContent = "http://www.laigome.com/store/item?storeCode=A46W&skuId=100001487";
			code = QRCode.from(qrcodeContent).to(ImageType.JPG).withHint(EncodeHintType.MARGIN, 0).withSize(8250, 8250);
			bufo = new BufferedOutputStream(new FileOutputStream(new File("D:/log/QR_sku" + i + ".jpg")));
			code.writeTo(bufo);
			bufo.flush();
			bufo.close();
			bufo = null;
			code = null;
			System.out.println(i + " end");
			System.out.println(new Date());

		}
		CountDownLatch countDownLatch = new CountDownLatch(1);
		countDownLatch.await();
	}
	

}
