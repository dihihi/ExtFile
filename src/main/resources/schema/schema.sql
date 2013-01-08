drop table t_article ;
drop table t_category ;
create table t_category(id varchar(32) primary key, name varchar(256) not null, leaf BOOLEAN, order_num integer, pid varchar(32));
create table t_article(id varchar(32) primary key, category_id varchar(32), title varchar(256) not null,content CLOB, create_time TIMESTAMP, mod_time TIMESTAMP,is_delete boolean default false);
insert into t_category(id, name, order_num, pid) values(0, 'ROOT', 0, '');
alter table t_article alter column content clob;
alter table t_article alter column is_delete set default false;
alter table t_category add column IF NOT EXISTS leaf BOOLEAN before order_num;
alter table t_article add column IF NOT EXISTS is_delete boolean;

create index t_category_pid on t_category(pid);
create index t_article_pid on t_article(category_id);
create index t_article_title on t_article(title);
#更新是叶子节点
update  t_category q set leaf=false where exists(select 1 from t_category t where t.pid=q.id) or exists(select 1 from t_article t where t.category_id=q.id);

delete from t_category  where id!='0';
delete from t_article;

#包含单引号会导致js错误
SELECT id,title FROM T_ARTICLE  where title like '%''%';
update T_ARTICLE set title=replace(title,'''','')  where title like '%''%';