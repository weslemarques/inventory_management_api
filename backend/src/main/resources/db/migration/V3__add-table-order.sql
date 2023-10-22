create table tb_order (id bigserial not null, primary key (id));
     create table tb_order_item (id bigserial not null, amount integer not null, product_id bigint, primary key (id));
    create table tb_order_items (order_id bigint not null, items_id bigint not null, primary key (order_id, items_id));

alter table if exists tb_order_item drop constraint if exists UK_3me2l2rffj0g61462ppkv0uka;

     alter table if exists tb_order_item add constraint UK_3me2l2rffj0g61462ppkv0uka unique (product_id);
     alter table if exists tb_order_items drop constraint if exists UK_dtwowy5in2wkvo4s9vronsobi;


     alter table if exists tb_order_items add constraint UK_dtwowy5in2wkvo4s9vronsobi unique (items_id);

     alter table if exists tb_order_item add constraint FK4h5xid5qehset7qwe5l9c997x foreign key (product_id) references tb_product;
     alter table if exists tb_order_items add constraint FKpq09h80y9cmt3e5yunksfwmg8 foreign key (items_id) references tb_order_item;
     alter table if exists tb_order_items add constraint FKm2jtpqpif3nvjyaap16os3xph foreign key (order_id) references tb_order;