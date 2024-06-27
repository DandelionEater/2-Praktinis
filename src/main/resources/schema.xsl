<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Template for transforming the root element -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Book Data</title>
                <style>
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid black; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h2>Book Data</h2>
                <xsl:apply-templates select="book"/>
            </body>
        </html>
    </xsl:template>

    <!-- Template for transforming the 'book' element -->
    <xsl:template match="book">
        <table>
            <tr>
                <th>Author</th>
                <th>Category</th>
                <th>ID</th>
                <th>InStock</th>
                <th>Name</th>
                <th>ReleaseYear</th>
            </tr>
            <tr>
                <td><xsl:value-of select="@Author"/></td>
                <td><xsl:value-of select="@Category"/></td>
                <td><xsl:value-of select="@ID"/></td>
                <td><xsl:value-of select="@InStock"/></td>
                <td><xsl:value-of select="@Name"/></td>
                <td><xsl:value-of select="@ReleaseYear"/></td>
            </tr>
        </table>
    </xsl:template>
</xsl:stylesheet>
