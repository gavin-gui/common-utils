package com.easycms.common.poi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.easycms.common.util.CommonUtility;

public class POIExcel {

	private static final Logger logger = Logger.getLogger(POIExcel.class);

	private static POIExcel instance = new POIExcel();

	private POIExcel() {
	}

	public static POIExcel getInstance() {
		return instance;
	}

	/**
	 * 初始化Excel表格
	 * 
	 * @param path
	 * @return
	 */
	public Workbook initExcel(File file) {
		InputStream in = null;
		Workbook workbook = null;
		boolean isXLSX = false;
		try {
			in = new FileInputStream(file);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
			hssfWorkbook.getNumberOfSheets();
			workbook = hssfWorkbook;
		} catch (OfficeXmlFileException e) {
			logger.info("==> It's a 2007 excel.");
			isXLSX = true;
			// e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// #########################################################
		// 若是xlsx格式则创建工作簿
		// #########################################################
		if (isXLSX) {
			try {
				in = new FileInputStream(file);
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
				xssfWorkbook.getNumberOfSheets();
				workbook = xssfWorkbook;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return workbook;
	}

	/**
	 * 是否是一个XSSFWorkbook对象
	 * 
	 * @param workbook
	 * @return 若为空，则不是
	 */
	public XSSFWorkbook isXSSFWorkbook(Workbook workbook) {
		if (workbook instanceof XSSFWorkbook) {
			return (XSSFWorkbook) workbook;
		} else {
			return null;
		}
	}

	/**
	 * 是否是一个HSSFWorkbook对象
	 * 
	 * @param workbook
	 * @return 若为空，则不是
	 */
	public HSSFWorkbook isHSSFWorkbook(Workbook workbook) {
		if (workbook instanceof HSSFWorkbook) {
			return (HSSFWorkbook) workbook;
		} else {
			return null;
		}
	}

	public XSSFWorkbook createXSSFWorkbook() {
		return new XSSFWorkbook();
	}

	public HSSFWorkbook createHSSFWorkbook() {
		return new HSSFWorkbook();
	}

	public void write(Workbook workbook, String absolutePath) {
		if (workbook != null && CommonUtility.isNonEmpty(absolutePath)) {
			try {
				OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(absolutePath)));
				workbook.write(out);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
