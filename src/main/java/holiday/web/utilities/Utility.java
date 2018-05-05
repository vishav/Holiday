package holiday.web.utilities;

import holiday.web.entities.CheckoutOrder;
import holiday.web.entities.Item;
import holiday.web.entities.TransactionResponse;
import holiday.web.entities.UserAccount;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utility {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    ;

    private static int totalColumns = 0;

    public static void downloadXLSX(List<TransactionResponse> transactionResponseList, HttpServletResponse response) {

        String fileName = "Transactions_Details_" + LocalDateTime.now().format(formatter).toString() + ".xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Transaction Details");

        String[] headers = {"Name", "Email", "Transaction Date", "Total", "Order Details"};

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);

        int rowNum = 0;

        Row headerRow = sheet.createRow(rowNum);

        // Creating cells
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        for (TransactionResponse transactionResponse : transactionResponseList) {
            CheckoutOrder checkoutOrder = transactionResponse.getCheckoutOrder();
            int colNum = 0;
            Row row = sheet.createRow(++rowNum);

            Cell dateCell = row.createCell(colNum++);
            dateCell.setCellValue(checkoutOrder.getDateTime());
            dateCell.setCellStyle(cs);

            Cell nameCell = row.createCell(colNum++);
            nameCell.setCellValue(transactionResponse.getFname() + " " + transactionResponse.getLname());
            nameCell.setCellStyle(cs);

            Cell emailCell = row.createCell(colNum++);
            emailCell.setCellValue(transactionResponse.getEmail());
            emailCell.setCellStyle(cs);

            Cell totalCell = row.createCell(colNum++);
            totalCell.setCellValue(checkoutOrder.getTotal());
            totalCell.setCellStyle(cs);

            createItemCells(checkoutOrder.getItem(), row, colNum, cs);

        }
        System.out.println("totalColumns:" + totalColumns);

        // Resize all columns to fit the content size
        for (int i = 0; i < totalColumns; i++) {
            sheet.autoSizeColumn(i);
        }


        if (workbook != null) {
            // Writing file to outputstream
            BufferedOutputStream bufferedOutputStream = null;
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename" + fileName);

                bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                workbook.write(bufferedOutputStream); } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void createItemCells(List<Item> items, Row row, int colNum, CellStyle cs) {
        for (Item item : items) {
            String orderDetails = "Country: " + item.getCountry();
            String range = "";

            String state = item.getState();
            String city = item.getCity();

            if (state.equalsIgnoreCase("State")) {
                state = "No state Selected";
            }

            if (city.equalsIgnoreCase("City")) {
                city = "No state Selected";
            }

            range += item.getFromMonth() + "/";

            range += item.getFromDay() + "/";

            range += item.getFromYear();

            range += " - ";

            range += item.getToMonth() + "/";

            range += item.getToDay() + "/";

            range += item.getToYear();

            orderDetails += "\n State: " + state + "\n City: " + city + "\n Range: " + range;

            Cell itemCell = row.createCell(colNum++);
            itemCell.setCellValue(orderDetails);
            itemCell.setCellStyle(cs);

            if (colNum > totalColumns) {
                totalColumns = colNum;
            }
        }
    }
}
