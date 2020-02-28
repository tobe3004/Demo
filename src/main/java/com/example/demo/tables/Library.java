/*
 * This file is generated by jOOQ.
 */
package com.example.demo.tables;


import com.example.demo.Public;
import com.example.demo.tables.records.LibraryRecord;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Library extends TableImpl<LibraryRecord> {

    private static final long serialVersionUID = 1951091779;

    /**
     * The reference instance of <code>public.library</code>
     */
    public static final Library LIBRARY = new Library();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LibraryRecord> getRecordType() {
        return LibraryRecord.class;
    }

    /**
     * The column <code>public.library.id</code>.
     */
    public final TableField<LibraryRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.library.name</code>.
     */
    public final TableField<LibraryRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * Create a <code>public.library</code> table reference
     */
    public Library() {
        this(DSL.name("library"), null);
    }

    /**
     * Create an aliased <code>public.library</code> table reference
     */
    public Library(String alias) {
        this(DSL.name(alias), LIBRARY);
    }

    /**
     * Create an aliased <code>public.library</code> table reference
     */
    public Library(Name alias) {
        this(alias, LIBRARY);
    }

    private Library(Name alias, Table<LibraryRecord> aliased) {
        this(alias, aliased, null);
    }

    private Library(Name alias, Table<LibraryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Library(Table<O> child, ForeignKey<O, LibraryRecord> key) {
        super(child, key, LIBRARY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Library as(String alias) {
        return new Library(DSL.name(alias), this);
    }

    @Override
    public Library as(Name alias) {
        return new Library(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Library rename(String name) {
        return new Library(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Library rename(Name name) {
        return new Library(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}