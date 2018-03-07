package com.bmtc.report.uitl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bmtc.report.dao.xmlToJavaObject;
import com.bmtc.report.domain.Kw;
import com.bmtc.report.domain.Msg;
import com.bmtc.report.domain.Test;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.document.RtfDocumentSettings;
import com.lowagie.text.rtf.style.RtfParagraphStyle;

public class CreateTestReport {

	private static final Logger logger = LoggerFactory
			.getLogger(CreateTestReport.class);

	public static List<Kw> kwList = null;

	// 自增标题样式
	public static final RtfParagraphStyle STYLE_HEADING_4 = new RtfParagraphStyle(
			"heading 4", "Normal");
	public static final RtfParagraphStyle STYLE_HEADING_5 = new RtfParagraphStyle(
			"heading 5", "Normal");
	public static final RtfParagraphStyle STYLE_HEADING_6 = new RtfParagraphStyle(
			"heading 6", "Normal");
	public static final RtfParagraphStyle STYLE_HEADING_7 = new RtfParagraphStyle(
			"heading 7", "Normal");
	//对标题进行注册
	static {
		STYLE_HEADING_4.setStyle(Font.BOLD);
		STYLE_HEADING_4.setSize(12);
		STYLE_HEADING_4.setFirstLineIndent(20);
		STYLE_HEADING_4.setSpacingAfter(20);

		STYLE_HEADING_5.setStyle(Font.BOLD);
		STYLE_HEADING_5.setSize(12);
		STYLE_HEADING_5.setFirstLineIndent(30);
		STYLE_HEADING_5.setSpacingAfter(20);

		STYLE_HEADING_6.setStyle(Font.BOLD);
		STYLE_HEADING_6.setSize(12);
		STYLE_HEADING_6.setFirstLineIndent(40);
		STYLE_HEADING_6.setSpacingAfter(20);

		STYLE_HEADING_7.setStyle(Font.BOLD);
		STYLE_HEADING_7.setSize(12);
		STYLE_HEADING_7.setFirstLineIndent(50);
		STYLE_HEADING_7.setSpacingAfter(20);
	}

	
	/**
	 * 生成测试报告
	 * 
	 * @param inputFilePath
	 * @param fileName
	 * @param outputFilePath
	 */
	public static void createTestReport(String inputFilePath, String fileName,
			String outputFilePath) {
		logger.info("CreateTestReport.createTestReport() start");
		try {
			Document document = new Document();
			RtfWriter2 writer;
			writer = RtfWriter2.getInstance(document, new FileOutputStream(
					outputFilePath + fileName + ".doc"));
			document.open();// 打开文档准备写入
			document.setMargins(90f, 90f, 72f, 72f);
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);

			RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
			rtfGsBt1.setAlignment(Element.ALIGN_CENTER);
			rtfGsBt1.setStyle(Font.BOLD);
			rtfGsBt1.setSize(20);
			rtfGsBt1.setSpacingBefore(30);
			rtfGsBt1.setSpacingAfter(20);

			RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
			rtfGsBt2.setStyle(Font.BOLD);
			rtfGsBt2.setSize(16);
			rtfGsBt2.setSpacingAfter(20);

			RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3;
			rtfGsBt3.setStyle(Font.BOLD);
			rtfGsBt3.setSize(14);
			rtfGsBt3.setSpacingAfter(20);
			rtfGsBt3.setFirstLineIndent(10);
			
			//对标题进行注册
			RtfDocumentSettings settings = writer.getDocumentSettings();
			settings.registerParagraphStyle(STYLE_HEADING_4);
			settings.registerParagraphStyle(STYLE_HEADING_5);
			settings.registerParagraphStyle(STYLE_HEADING_6);
			settings.registerParagraphStyle(STYLE_HEADING_7);
			RtfParagraphStyle rtfGsBt4 = STYLE_HEADING_4;
			RtfParagraphStyle rtfGsBt5 = STYLE_HEADING_5;
			RtfParagraphStyle rtfGsBt6 = STYLE_HEADING_6;
			RtfParagraphStyle rtfGsBt7 = STYLE_HEADING_7;
			
			//设置字体
			Font font = new Font(bfChinese, 12, Font.NORMAL);

			String pathPrefix = inputFilePath.substring(0,
					inputFilePath.lastIndexOf("output.xml") - 1);

			List<Test> tests = xmlToJavaObject.stringToJava(inputFilePath);
			for (Test test : tests) {
				Paragraph title = new Paragraph(test.getName(), rtfGsBt1);
				document.add(title);
				List<Kw> kws1 = test.getKw();
				int i = 1;
				for (Kw kw1 : kws1) {
					if ("for".equals(kw1.getType())
							|| "foritem".equals(kw1.getType())) {
						break;
					} else {
						Paragraph childrenTitle1 = new Paragraph(i + ".  "
								+ kw1.getName(), rtfGsBt2);
						document.add(childrenTitle1);
						if (kw1.getMsg() != null && !kw1.getMsg().isEmpty()) {
							List<Msg> msgs1 = kw1.getMsg();
							for (Msg msg1 : msgs1) {
								if (msg1.getHtml() == null
										&& "FAIL".equals(msg1.getLevel())) {
									Paragraph context = new Paragraph();
									context.add(msg1.getContent());
									context.setFont(font);
									context.setSpacingAfter(20);
									context.setFirstLineIndent(40);
									document.add(context);
								}
								if ("yes".equals(msg1.getHtml())) {
									String str = msg1.getContent().substring(
													msg1.getContent().indexOf("src") + 5,
													msg1.getContent().lastIndexOf("png") + 3);
									String src = str.replaceAll("\\\\", "/")
											.replaceAll("\\./", pathPrefix);
									Paragraph context = new Paragraph();
									Image img = Image.getInstance(src);
									img.scalePercent(20);
									context.add(img);
									context.setAlignment(Element.ALIGN_CENTER);
									document.add(context);

								}
							}
						}
						if (kw1.getKw() != null && !kw1.getKw().isEmpty()) {
							List<Kw> kws2 = kw1.getKw();
							int j = 1;
							for (Kw kw2 : kws2) {
								if ("for".equals(kw2.getType())
										|| "foritem".equals(kw2.getType())) {
									break;
								} else {
									Paragraph childrenTitle2 = new Paragraph(i
											+ "." + j + "  " + kw2.getName(),
											rtfGsBt3);
									document.add(childrenTitle2);
									if (kw2.getMsg() != null
											&& !kw2.getMsg().isEmpty()) {
										List<Msg> msgs2 = kw2.getMsg();
										for (Msg msg2 : msgs2) {
											if (msg2.getHtml() == null&& "FAIL".equals(msg2
															.getLevel())) {
												Paragraph context = new Paragraph();
												context.add(msg2.getContent());
												context.setFont(font);
												context.setSpacingAfter(20);
												context.setFirstLineIndent(40);
												document.add(context);
											}
											if ("yes".equals(msg2.getHtml())) {
												String str = msg2.getContent()
														.substring(msg2.getContent()
															.indexOf("src") + 5,
																msg2.getContent().lastIndexOf(
																		"png") + 3);
												String src = str.replaceAll("\\\\", "/")
														.replaceAll("\\./",pathPrefix);
												Paragraph context = new Paragraph();
												Image img = Image.getInstance(src);
												img.scalePercent(20);
												context.add(img);
												context.setSpacingAfter(20);
												context.setAlignment(Element.ALIGN_CENTER);
												document.add(context);
											}
										}
									}
									if (kw2.getKw() != null
											&& !kw2.getKw().isEmpty()) {
										List<Kw> kws3 = kw2.getKw();
										int k = 1;
										for (Kw kw3 : kws3) {
											if ("for".equals(kw3.getType())|| "foritem".equals(kw3
															.getType())) {
												break;
											} else {
												Paragraph childrenTitle3 = new Paragraph(
														i + "." + j + "." + k+ "  "
															+ kw3.getName(),rtfGsBt4);
												document.add(childrenTitle3);
												if (kw3.getMsg() != null
														&& !kw3.getMsg().isEmpty()) {
													List<Msg> msgs3 = kw3.getMsg();
													for (Msg msg3 : msgs3) {
														if (msg3.getHtml() == null&& "FAIL"
																.equals(msg3.getLevel())) {
															Paragraph context = new Paragraph();
															context.add(msg3.getContent());
															context.setFont(font);
															context.setSpacingAfter(20);
															context.setFirstLineIndent(40);
															document.add(context);
														}
														if ("yes".equals(msg3.getHtml())) {
															String str = msg3.getContent().substring(
																			msg3.getContent().indexOf("src") + 5,
																			msg3.getContent().lastIndexOf("png") + 3);
															String src = str.replaceAll("\\\\","/")
																	.replaceAll("\\./",pathPrefix);
															Paragraph context = new Paragraph();
															Image img = Image.getInstance(src);
															img.scalePercent(20);
															context.add(img);
															context.setSpacingAfter(20);
															context.setAlignment(Element.ALIGN_CENTER);
															document.add(context);
														}
													}
												}
												if (kw3.getKw() != null&& !kw3.getKw().isEmpty()) {
													List<Kw> kws4 = kw3.getKw();
													int l = 1;
													for (Kw kw4 : kws4) {
														if ("for".equals(kw4.getType())|| "foritem".equals(kw4.getType())) {
															break;
														} else {
															Paragraph childrenTitle4 = new Paragraph(
																	i+ "."+ j+ "."+ k+ "."+ l+ "  "+ kw4.getName(),rtfGsBt5);
															document.add(childrenTitle4);
															if (kw4.getMsg() != null&& !kw4.getMsg().isEmpty()) {
																List<Msg> msgs4 = kw4.getMsg();
																for (Msg msg4 : msgs4) {
																	if (msg4.getHtml() == null&& "FAIL".equals(msg4.getLevel())) {
																		Paragraph context = new Paragraph();
																		context.add(msg4.getContent());
																		context.setFont(font);
																		context.setSpacingAfter(20);
																		context.setFirstLineIndent(40);
																		document.add(context);
																	}
																	if ("yes".equals(msg4.getHtml())) {
																		String str = msg4.getContent().substring(
																			msg4.getContent().indexOf("src") + 5,
																						msg4.getContent().lastIndexOf("png") + 3);
																		String src = str.replaceAll("\\\\","/").replaceAll(
																					"\\./",pathPrefix);
																		Paragraph context = new Paragraph();
																		Image img = Image.getInstance(src);
																		img.scalePercent(20);
																		context.add(img);
																		context.setSpacingAfter(20);
																		context.setAlignment(Element.ALIGN_CENTER);
																		document.add(context);
																	}
																}
															}
															if (kw4.getKw() != null && !kw4.getKw().isEmpty()) {
																List<Kw> kws5 = kw4.getKw();
																int m = 1;
																for (Kw kw5 : kws5) {
																	if ("for".equals(kw5.getType())|| "foritem".equals(kw5.getType())) {
																		break;
																	} else {
																		Paragraph childrenTitle5 = new Paragraph(
																				i+ "."+ j+ "."+ k+ "."+ l+ "."+ m+ "  "+ kw5.getName(),rtfGsBt6);
																		document.add(childrenTitle5);
																		if (kw5.getMsg() != null&& !kw5.getMsg().isEmpty()) {
																			List<Msg> msgs5 = kw5.getMsg();
																			for (Msg msg5 : msgs5) {
																				if (msg5.getHtml() == null && "FAIL".equals(msg5.getLevel())) {
																					Paragraph context = new Paragraph();
																					context.add(msg5.getContent());
																					context.setFont(font);
																					context.setSpacingAfter(20);
																					context.setFirstLineIndent(40);
																					document.add(context);
																				}
																				if ("yes".equals(msg5.getHtml())) {
																					String str = msg5.getContent().substring(
																							msg5.getContent().indexOf("src") + 5,
																								msg5.getContent().lastIndexOf("png") + 3);
																					String src = str.replaceAll("\\\\","/").replaceAll(
																									"\\./",pathPrefix);
																					Paragraph context = new Paragraph();
																					Image img = Image.getInstance(src);
																					img.scalePercent(20);
																					context.add(img);
																					context.setSpacingAfter(20);
																					context.setAlignment(Element.ALIGN_CENTER);
																					document.add(context);
																				}
																			}
																		}
																		kwList = new ArrayList<>();
																		List<Kw> kws6 = getKw(kw5);
																		int n = 1;
																		for (Kw kw6 : kws6) {
																			if ("for".equals(kw6.getType())|| "foritem".equals(kw6.getType())) {
																				break;
																			} else {
																				Paragraph childrenTitle6 = new Paragraph(
																						i+ "."+ j+ "."+ k+ "."+ l+ "."+ m+ "."+ n+ "  "+ kw6.getName(),rtfGsBt7);
																				document.add(childrenTitle6);
																				if (kw6.getMsg() != null && !kw6.getMsg().isEmpty()) {
																					List<Msg> msgs6 = kw6.getMsg();
																					for (Msg msg6 : msgs6) {
																						if (msg6.getHtml() == null && "FAIL".equals(msg6.getLevel())) {
																							Paragraph context = new Paragraph();
																							context.add(msg6.getContent());
																							context.setFont(font);
																							context.setSpacingAfter(20);
																							context.setFirstLineIndent(40);
																							document.add(context);
																						}
																						if ("yes".equals(msg6.getHtml())) {
																							String str = msg6.getContent().substring(
																											msg6.getContent().indexOf("src") + 5,
																											msg6.getContent().lastIndexOf("png") + 3);
																							String src = str.replaceAll("\\\\","/")
																									.replaceAll("\\./",pathPrefix);
																							Paragraph context = new Paragraph();
																							Image img = Image.getInstance(src);
																							img.scalePercent(20);
																							context.add(img);
																							context.setSpacingAfter(20);
																							context.setAlignment(Element.ALIGN_CENTER);
																							document.add(context);
																						}
																					}
																				}
																			}
																			n++;
																		}
																	}
																	m++;
																}
															}
														}
														l++;
													}
												}
											}
											k++;
										}
									}

								}
								j++;
							}
						}
					}
					i++;
				}
			}

			document.close();

			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("无法找到所要读取的文件");
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		logger.info("CreateTestReport.createTestReport() end");
	}
	
	/**
	 * 递归遍历kw对象
	 * @param kws
	 * @return
	 */
	private static List<Kw> getKw(Kw kws) {
		logger.info("CreateTestReport.getKw() start");
		if (kws.getKw() != null && !kws.getKw().isEmpty()) {
			List<Kw> kws5 = kws.getKw();
			for (Kw kw5 : kws5) {
				if ("for".equals(kw5.getType())
						|| "foritem".equals(kw5.getType())) {
					break;
				} else {
					kwList.add(kw5);
					if (kw5.getKw() != null && !kw5.getKw().isEmpty()) {
						getKw(kw5);
					}
				}

			}
		}
		logger.info("CreateTestReport.getKw() end");
		return kwList;
	}

}
