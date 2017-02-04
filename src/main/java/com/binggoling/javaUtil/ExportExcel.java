package com.binggoling.javaUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {

	public HSSFWorkbook exportExcel(String workSheetName, String[] titles,String[] propert,List<T> dataset,Class<?> clazz) {
		return export(workSheetName, titles, propert, dataset, "", clazz);
	}

	/**
	 * 导出xls文件，考虑到一般只是导出某种对象信息，所以使用泛型去解析数据，只用传入需要导出的字段及表头信息，以及数据，所以这样可以完全地专注于业务，
	 * 同时有可能对于某些字段可能要设置一些默认值
	 * ，这样当数据为null时，就可以设置，还有一个问题就是有些字段可能需要进行一些转换，比如性别用0和1表示，这样导出的时候就可以显示为男和女。
	 * 
	 * 
	 * @param workSheetName
	 *            工作表名称，涉及到一个表只能有65536行，256列，所以，当数据量较大时会直接写入下一个表。列超出则直接抛出异常。
	 * @param titles
	 *            表头信息
	 * @param property
	 *            对象属性，对对象的哪些属性进行导出
	 * @param data
	 *            数据集合
	 * @param defaultValue
	 *            设置默认值
	 * @param clazz
	 *            进行类型转换的类，需要注意的是方法必须使用get+属性名（首字母大写）。参数为属性类型。如username为String类型
	 *            ，则方法为public String getUsername(String
	 *            username),返回值原则上可以为任何类型，返回以后直接进行String
	 *            .valuOf()操作。如果使用转换类，则所有字段必须全部包含，不需要转换的字段直接返回。
	 *            考虑到一般情况下可能输出字段较多，进行转换的字段不是很多，所以提供该类的生成工具类
	 */
	public HSSFWorkbook export(String workSheetName, String[] titles, String[] property, List<?> data, String defaultValue, Class<?> clazz) {
		
		// 创建工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		// 每个sheet中的总显示数（最大行数）
		int sheetMaxRowCount = 65534;
		// 每个sheet的列数
		int columNumber = titles.length;
		try {
			HSSFCellStyle setBorder = wb.createCellStyle();
			// sheet索引
			int sheetNum = 1;
			// 行
			int rowNum = 0;
			// 声明工作表
			HSSFSheet sheet = null;
			for (Object bean : data) {
				// 此处加1.00，用于强制转换类型留下一行用于表头信息，然后肯定是要向上进位了
				Double sheetIndex = Math.ceil((rowNum + 1.00)/ sheetMaxRowCount);
				// 返回的Double，当然要取int类型了
				sheetNum = sheetIndex.intValue();
				HSSFRow rowData = null;// 表内容k
				// 如果行数刚好是最大行数的整数倍，就应该加一个工作表了
				if (rowNum == sheetMaxRowCount * (sheetNum - 1)) {
					// sheet表名依次为表0、表1、表2...
					sheet = wb.createSheet(workSheetName + sheetNum);
					// 创建行
					rowData = sheet.createRow(0);
					// 在表头每列 放值
					for (int columnIndex = 0; columnIndex < columNumber; columnIndex++) {
						// 按照表头信息生成每一个表头单元格信息
						HSSFCell headerCell = rowData.createCell(columnIndex);
						// 设置每一列的宽度
						sheet.setColumnWidth(columnIndex, 6000);
						// 居中
						setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						// 设置单元格值
						headerCell.setCellValue(titles[columnIndex]);
					}
				}
				// 创建行
				rowData = sheet.createRow(rowNum - sheetMaxRowCount* (sheetNum - 1) + 1);
				for (int columnIndex = 0; columnIndex < columNumber; columnIndex++) {
					// 按照表头信息生成每一个表头单元格信息
					HSSFCell cellData = rowData.createCell(columnIndex);
					// 设置每一列的宽度
					sheet.setColumnWidth(columnIndex, 6000);
					// 居中
					setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					// 根据传入的需要导出的字段名称取得集合对象中对应字段对象
					Field field = bean.getClass().getDeclaredField(
							property[columnIndex]);
					// 有可能是private对象，无法直接访问，所以要先设置为可以访问
					field.setAccessible(true);
					// 根据字段对象取得集合中每一个对象的属性值
					if (field.get(bean) != null) {
						// 考虑到有些字段需要进行数据转换，而又不知道转换的方法是哪个类，哪个方法，所以固定转换方法为“get”+属性名（首字母大写）。参数为属性类型。如username为String类型
						// ，则方法为public String getUsername(String
						// username),返回值原则上可以为任何类型，返回以后直接进行String.valuOf()操作
						String getMethodName = "get"
								+ field.getName().substring(0, 1).toUpperCase()
								+ field.getName().substring(1);
						Method method = clazz.getDeclaredMethod(getMethodName,new Class[]{});
						
						Object value = method.invoke(bean, new Object[] {});
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value instanceof Boolean) {
							boolean bValue = (Boolean) value;
							textValue = "是";
							if (!bValue) {
								textValue = "否";
							}
						}else{
							textValue = value.toString();
						}
						cellData.setCellValue(textValue);
						
					} else {
						// 如果值为null，则有可能会设置为默认值的
						cellData.setCellValue(defaultValue);
					}
				}
				// 行数当然要加1了。
				rowNum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
}
