package com.parcialspring.parcialspring.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.parcialspring.parcialspring.dto.ProductRequest;
import com.parcialspring.parcialspring.dto.ProductResponse;
import com.parcialspring.parcialspring.model.ProductModel;
import com.parcialspring.parcialspring.model.SupplierModel;
import com.parcialspring.parcialspring.repository.ProductRepository;
import com.parcialspring.parcialspring.repository.SupplierRepository;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Data
public class ProductService {

    private final ProductRepository repository;
    private final SupplierRepository supplierRepository;

    ProductService(ProductRepository repository, SupplierRepository supplierRepository) {
        this.repository = repository;
        this.supplierRepository = supplierRepository;
    }

    // Métodos del servicio

    // Metodo para Crear producto usando Repository Save
    public ProductResponse createProduct(ProductRequest request) {
        // Buscar el proveedor por ID
        SupplierModel supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id " + request.getSupplierId()));

        ProductModel product = new ProductModel();
        product.setProductId(request.getProductId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());
        product.setStock(request.getStock());
        product.setProductType(request.getProductType());
        product.setSupplier(supplier);

        ProductModel newProduct = repository.save(product);

        return new ProductResponse(
                newProduct.getId(),
                newProduct.getProductId(),
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getUnitPrice(),
                newProduct.getStock(),
                newProduct.getProductType(),
                newProduct.getSupplier().getId(),
                newProduct.getCreatedAt(),
                newProduct.getUpdatedAt()
        );
    }

    // Metodo para Listar todos los productos
    public List<ProductResponse> getAllProducts() {
        List<ProductModel> products = repository.findAll();

        return products.stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getProductId(),
                        p.getName(),
                        p.getDescription(),
                        p.getUnitPrice(),
                        p.getStock(),
                        p.getProductType(),
                        p.getSupplier().getId(),
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                )).toList();
    }

    // Metodo para Buscar producto por id
    public ProductResponse getProductById(Long id) {
        ProductModel product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado por id " + id));

        return new ProductResponse(
                product.getId(),
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getStock(),
                product.getProductType(),
                product.getSupplier().getId(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    // Metodo para Actualizar producto por id
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        ProductModel product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado por id " + id));

        // Buscar el proveedor por ID si se proporciona uno nuevo
        if (request.getSupplierId() != null) {
            SupplierModel supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id " + request.getSupplierId()));
            product.setSupplier(supplier);
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());
        product.setStock(request.getStock());
        product.setProductType(request.getProductType());

        ProductModel updatedProduct = repository.save(product);

        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getProductId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getUnitPrice(),
                updatedProduct.getStock(),
                updatedProduct.getProductType(),
                updatedProduct.getSupplier().getId(),
                updatedProduct.getCreatedAt(),
                updatedProduct.getUpdatedAt()
        );
    }

    // Metodo para Eliminar producto por id
    public void deleteProductById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado por id " + id);
        }
        repository.deleteById(id);
    }

    // ========== EXPORTACIÓN A EXCEL ==========
    public byte[] exportToExcel() throws IOException {
        List<ProductModel> products = repository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Registro de Productos");

            // ========== ESTILOS ==========

            // Estilo para el título principal
            CellStyle titleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 18);
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleStyle.setFont(titleFont);
            titleStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para el subtítulo (fecha)
            CellStyle subtitleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font subtitleFont = workbook.createFont();
            subtitleFont.setFontHeightInPoints((short) 11);
            subtitleFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
            subtitleStyle.setFont(subtitleFont);
            subtitleStyle.setAlignment(HorizontalAlignment.CENTER);
            subtitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Estilo para encabezados de tabla
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 11);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setBorderTop(BorderStyle.MEDIUM);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para celdas de datos (alternado)
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dataStyleAlt = workbook.createCellStyle();
            dataStyleAlt.cloneStyleFrom(dataStyle);
            dataStyleAlt.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            dataStyleAlt.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Estilo para números
            CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.cloneStyleFrom(dataStyle);
            numberStyle.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle numberStyleAlt = workbook.createCellStyle();
            numberStyleAlt.cloneStyleFrom(dataStyleAlt);
            numberStyleAlt.setAlignment(HorizontalAlignment.RIGHT);

            // ========== CREAR ENCABEZADO ==========

            int currentRow = 0;

            // Título principal
            org.apache.poi.ss.usermodel.Row titleRow = sheet.createRow(currentRow++);
            titleRow.setHeightInPoints(30);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("REPORTE DE PRODUCTOS LÁCTEOS");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

            // Fecha de generación
            org.apache.poi.ss.usermodel.Row dateRow = sheet.createRow(currentRow++);
            dateRow.setHeightInPoints(20);
            org.apache.poi.ss.usermodel.Cell dateCell = dateRow.createCell(0);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");
            dateCell.setCellValue("Generado: " + now.format(formatter));
            dateCell.setCellStyle(subtitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

            // Fila vacía
            currentRow++;

            // ========== ENCABEZADOS DE TABLA ==========

            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(currentRow++);
            headerRow.setHeightInPoints(25);

            String[] columns = {
                "ID", "ID Producto", "Nombre", "Descripción", "Precio Unitario",
                "Stock", "Tipo Producto", "ID Proveedor", "Fecha Creación", "Última Actualización"
            };

            for (int i = 0; i < columns.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // ========== LLENAR DATOS ==========

            int rowNum = currentRow;
            for (ProductModel product : products) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum);
                row.setHeightInPoints(20);
                boolean isAlt = (rowNum - currentRow) % 2 == 1;

                CellStyle currentDataStyle = isAlt ? dataStyleAlt : dataStyle;
                CellStyle currentNumberStyle = isAlt ? numberStyleAlt : numberStyle;

                // Datos
                createStyledCell(row, 0, String.valueOf(product.getId()), currentNumberStyle);
                createStyledCell(row, 1, product.getProductId() != null ? product.getProductId() : "", currentDataStyle);
                createStyledCell(row, 2, product.getName() != null ? product.getName() : "", currentDataStyle);
                createStyledCell(row, 3, product.getDescription() != null ? product.getDescription() : "", currentDataStyle);
                createStyledCell(row, 4, product.getUnitPrice() != null ? String.format("$%d", product.getUnitPrice()) : "$0", currentNumberStyle);
                createStyledCell(row, 5, String.valueOf(product.getStock() != null ? product.getStock() : 0), currentNumberStyle);
                createStyledCell(row, 6, product.getProductType() != null ? product.getProductType() : "", currentDataStyle);
                createStyledCell(row, 7, String.valueOf(product.getSupplier().getId()), currentNumberStyle);
                createStyledCell(row, 8, product.getCreatedAt() != null ? product.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "", currentDataStyle);
                createStyledCell(row, 9, product.getUpdatedAt() != null ? product.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "", currentDataStyle);

                rowNum++;
            }

            // ========== AJUSTAR ANCHO DE COLUMNAS ==========

            int[] columnWidths = {8, 15, 25, 35, 15, 10, 20, 15, 18, 18};
            for (int i = 0; i < columnWidths.length; i++) {
                sheet.setColumnWidth(i, columnWidths[i] * 256);
            }

            // ========== FOOTER CON TOTALES ==========

            org.apache.poi.ss.usermodel.Row footerRow = sheet.createRow(rowNum + 1);
            footerRow.setHeightInPoints(22);
            org.apache.poi.ss.usermodel.Cell footerCell = footerRow.createCell(0);
            footerCell.setCellValue("Total de productos: " + products.size());
            CellStyle footerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font footerFont = workbook.createFont();
            footerFont.setBold(true);
            footerFont.setFontHeightInPoints((short) 11);
            footerStyle.setFont(footerFont);
            footerCell.setCellStyle(footerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 5));

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // ========== EXPORTACIÓN A PDF ==========
    public byte[] exportToPdf() throws IOException {
        List<ProductModel> products = repository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Configurar documento en orientación horizontal
            Document document = new Document(PageSize.A4.rotate());
            document.setMargins(20, 20, 40, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            // ========== ENCABEZADO PRINCIPAL ==========

            // Definir color corporativo (igual que cabras)
            Color headerColor = new Color(107, 124, 69); // Color verde oliva
            Color headerTextColor = Color.WHITE;
            Color accentColor = new Color(90, 107, 53); // Versión más oscura

            // Título principal con fondo
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingAfter(10);

            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            titleFont.setColor(headerTextColor);

            PdfPCell titleCell = new PdfPCell(new Phrase("REPORTE DE PRODUCTOS LÁCTEOS", titleFont));
            titleCell.setBackgroundColor(headerColor);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setPadding(15);
            titleCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(titleCell);

            document.add(headerTable);

            // ========== INFORMACIÓN DE FECHA Y TOTALES ==========

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(15);
            infoTable.setWidths(new float[]{1, 1});

            com.lowagie.text.Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            infoFont.setColor(new Color(80, 80, 80));

            com.lowagie.text.Font infoBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            // Fecha de generación
            PdfPCell dateCell = new PdfPCell();
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setPadding(5);
            Phrase datePhrase = new Phrase();
            datePhrase.add(new Chunk("Fecha de generación: ", infoBoldFont));
            datePhrase.add(new Chunk(now.format(formatter), infoFont));
            dateCell.addElement(datePhrase);
            infoTable.addCell(dateCell);

            // Total de registros
            PdfPCell totalCell = new PdfPCell();
            totalCell.setBorder(Rectangle.NO_BORDER);
            totalCell.setPadding(5);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            Phrase totalPhrase = new Phrase();
            totalPhrase.add(new Chunk("Total de productos: ", infoBoldFont));
            totalPhrase.add(new Chunk(String.valueOf(products.size()), infoFont));
            totalCell.addElement(totalPhrase);
            infoTable.addCell(totalCell);

            document.add(infoTable);

            // ========== TABLA DE DATOS ==========

            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5);

            // Anchos de columnas
            float[] columnWidths = {0.6f, 1.2f, 1.8f, 2.5f, 1.2f, 0.8f, 1.5f, 1f, 1.5f, 1.5f};
            table.setWidths(columnWidths);

            // ========== ENCABEZADOS ==========

            com.lowagie.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            headerFont.setColor(Color.WHITE);

            String[] headers = {
                "ID", "ID Producto", "Nombre", "Descripción", "Precio Unit.",
                "Stock", "Tipo", "ID Prov.", "Creado", "Actualizado"
            };

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(accentColor); // Mismo color que cabras
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                cell.setBorderColor(new Color(200, 200, 200));
                cell.setBorderWidth(1);
                table.addCell(cell);
            }

            // ========== DATOS ==========

            com.lowagie.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            com.lowagie.text.Font dataBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);

            Color rowColor1 = Color.WHITE;
            Color rowColor2 = new Color(245, 245, 245);

            int rowIndex = 0;
            for (ProductModel product : products) {
                Color currentRowColor = rowIndex % 2 == 0 ? rowColor1 : rowColor2;

                // ID
                addDataCell(table, String.valueOf(product.getId()), dataBoldFont, currentRowColor, Element.ALIGN_CENTER);

                // ID Producto
                addDataCell(table, product.getProductId() != null ? product.getProductId() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                // Nombre
                addDataCell(table, product.getName() != null ? product.getName() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                // Descripción
                addDataCell(table, product.getDescription() != null ? product.getDescription() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                // Precio
                addDataCell(table, product.getUnitPrice() != null ? String.format("$%d", product.getUnitPrice()) : "$0", dataFont, currentRowColor, Element.ALIGN_RIGHT);

                // Stock
                addDataCell(table, String.valueOf(product.getStock() != null ? product.getStock() : 0), dataFont, currentRowColor, Element.ALIGN_CENTER);

                // Tipo
                addDataCell(table, product.getProductType() != null ? product.getProductType() : "", dataFont, currentRowColor, Element.ALIGN_LEFT);

                // ID Proveedor
                addDataCell(table, String.valueOf(product.getSupplier().getId()), dataFont, currentRowColor, Element.ALIGN_CENTER);

                // Fecha Creación
                addDataCell(table, product.getCreatedAt() != null ? product.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "", dataFont, currentRowColor, Element.ALIGN_CENTER);

                // Fecha Actualización
                addDataCell(table, product.getUpdatedAt() != null ? product.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "", dataFont, currentRowColor, Element.ALIGN_CENTER);

                rowIndex++;
            }

            document.add(table);

            // ========== FOOTER ==========

            document.add(new Paragraph("\n"));

            com.lowagie.text.Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            footerFont.setColor(new Color(128, 128, 128));

            Paragraph footer = new Paragraph("Reporte generado automáticamente por el Sistema de Gestión de Productos", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            return out.toByteArray();
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    // Método helper para crear celdas con estilo en Excel
    private void createStyledCell(org.apache.poi.ss.usermodel.Row row, int column, String value, CellStyle style) {
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    // Método helper para agregar celdas de datos en PDF
    private void addDataCell(PdfPTable table, String value, com.lowagie.text.Font font, Color backgroundColor, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setBackgroundColor(backgroundColor);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderColor(new Color(220, 220, 220));
        table.addCell(cell);
    }
}
