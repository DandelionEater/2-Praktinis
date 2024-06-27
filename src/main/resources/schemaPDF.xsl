<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <!-- Template for transforming the root element -->
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simple" page-height="29.7cm" page-width="21cm" margin="2cm">
                    <fo:region-body margin="1cm"/>
                    <fo:region-before extent="1cm"/>
                    <fo:region-after extent="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="simple">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="sans-serif" font-size="12pt">

                        <!-- Book Data -->
                        <fo:block font-size="14pt" font-weight="bold" space-after="0.5cm">Book Data</fo:block>
                        <xsl:apply-templates select="book"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <!-- Template for transforming the 'book' element -->
    <xsl:template match="book">
        <fo:table table-layout="fixed" width="100%" border="1pt solid black" margin-bottom="1cm">
            <fo:table-column column-width="16.66%"/>
            <fo:table-column column-width="16.66%"/>
            <fo:table-column column-width="16.66%"/>
            <fo:table-column column-width="16.66%"/>
            <fo:table-column column-width="16.66%"/>
            <fo:table-column column-width="16.66%"/>
            <fo:table-header>
                <fo:table-row background-color="#f2f2f2">
                    <fo:table-cell><fo:block>Author</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block>Category</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block>ID</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block>InStock</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block>Name</fo:block></fo:table-cell>
                    <fo:table-cell><fo:block>ReleaseYear</fo:block></fo:table-cell>
                </fo:table-row>
            </fo:table-header>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell><fo:block><xsl:value-of select="@Author"/></fo:block></fo:table-cell>
                    <fo:table-cell><fo:block><xsl:value-of select="@Category"/></fo:block></fo:table-cell>
                    <fo:table-cell><fo:block><xsl:value-of select="@ID"/></fo:block></fo:table-cell>
                    <fo:table-cell><fo:block><xsl:value-of select="@InStock"/></fo:block></fo:table-cell>
                    <fo:table-cell><fo:block><xsl:value-of select="@Name"/></fo:block></fo:table-cell>
                    <fo:table-cell><fo:block><xsl:value-of select="@ReleaseYear"/></fo:block></fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>

</xsl:stylesheet>
