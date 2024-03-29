/*
 * This file is generated by jOOQ.
 */
package ehroute.identityservice.entities.db.tables;


import ehroute.identityservice.entities.db.Keys;
import ehroute.identityservice.entities.db.Public;
import ehroute.identityservice.entities.db.tables.records.AccountsRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Accounts extends TableImpl<AccountsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.accounts</code>
     */
    public static final Accounts ACCOUNTS = new Accounts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccountsRecord> getRecordType() {
        return AccountsRecord.class;
    }

    /**
     * The column <code>public.accounts.id</code>.
     */
    public final TableField<AccountsRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.accounts.email</code>.
     */
    public final TableField<AccountsRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.accounts.address</code>.
     */
    public final TableField<AccountsRecord, String> ADDRESS = createField(DSL.name("address"), SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>public.accounts.public_key</code>.
     */
    public final TableField<AccountsRecord, byte[]> PUBLIC_KEY = createField(DSL.name("public_key"), SQLDataType.BLOB.nullable(false), this, "");

    /**
     * The column <code>public.accounts.password</code>.
     */
    public final TableField<AccountsRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.accounts.last_login</code>.
     */
    public final TableField<AccountsRecord, LocalDateTime> LAST_LOGIN = createField(DSL.name("last_login"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.accounts.updated_on</code>.
     */
    public final TableField<AccountsRecord, LocalDateTime> UPDATED_ON = createField(DSL.name("updated_on"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.accounts.created_on</code>.
     */
    public final TableField<AccountsRecord, LocalDateTime> CREATED_ON = createField(DSL.name("created_on"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    private Accounts(Name alias, Table<AccountsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Accounts(Name alias, Table<AccountsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.accounts</code> table reference
     */
    public Accounts(String alias) {
        this(DSL.name(alias), ACCOUNTS);
    }

    /**
     * Create an aliased <code>public.accounts</code> table reference
     */
    public Accounts(Name alias) {
        this(alias, ACCOUNTS);
    }

    /**
     * Create a <code>public.accounts</code> table reference
     */
    public Accounts() {
        this(DSL.name("accounts"), null);
    }

    public <O extends Record> Accounts(Table<O> child, ForeignKey<O, AccountsRecord> key) {
        super(child, key, ACCOUNTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<AccountsRecord, Long> getIdentity() {
        return (Identity<AccountsRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<AccountsRecord> getPrimaryKey() {
        return Keys.ACCOUNTS_PKEY;
    }

    @Override
    public List<UniqueKey<AccountsRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.ACCOUNTS_EMAIL_KEY);
    }

    @Override
    public Accounts as(String alias) {
        return new Accounts(DSL.name(alias), this);
    }

    @Override
    public Accounts as(Name alias) {
        return new Accounts(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Accounts rename(String name) {
        return new Accounts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Accounts rename(Name name) {
        return new Accounts(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, String, String, byte[], String, LocalDateTime, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
