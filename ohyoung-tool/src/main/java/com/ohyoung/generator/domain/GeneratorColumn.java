package com.ohyoung.generator.domain;

import com.ohyoung.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 *  表字段配置, 用于生成前端代码时是否在数据表格中显示以及是否为查询条件等
 * @author ohYoung
 * @date 2020/7/27 20:49
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "generator_column")
public class GeneratorColumn extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "is_nullable")
    private Boolean nullable;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "column_type")
    private String columnType;

    @Column(name = "column_comment")
    private String columnComment;

    @Column(name = "is_table_content")
    private Boolean tableContent;

    @Column(name = "form_type")
    private String formType;

    @Column(name = "query_type")
    private String queryType;

    @Transient
    private String javaDataType;

    @Transient
    private String columnHumpName;

    public GeneratorColumn(String tableName, String columnName, Boolean nullable, String columnType, String columnComment) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.nullable = nullable;
        this.columnType = columnType;
        this.columnComment = columnComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        GeneratorColumn column = (GeneratorColumn) o;
        return Objects.equals(tableName, column.getTableName()) && Objects.equals(columnName, column.getColumnName())
                && Objects.equals(nullable, column.getNullable()) && Objects.equals(columnType, column.getColumnType())
                && Objects.equals(columnComment, column.getColumnComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, columnName, nullable, columnType, columnComment);
    }
}
