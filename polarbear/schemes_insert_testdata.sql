insert into Admin(`id`, `name`, `pwd`, `createTime`, `loginTime`) values('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1456221513', '1456221513');
insert into user(`id`, `name`, `pwd`, `cellphone`, `createTime`) values('1', '极地鸥', 'e10adc3949ba59abbe56e057f20f883e', '13717686218', '1456221513');
insert into Category(`id`, `cg_desc`) values('3', '玩具');
insert into Category(`id`, `cg_desc`) values('2', '化妆品');
insert into Category(`id`, `cg_desc`) values('1', '服装');
insert into product_style(`id`, `styleProperties`) values('1', '{"[{"name"":""颜色"",""values"":"["红色"",""黄色"]}","{"name"":""大小"",""values"":"["M"",""L"",""XL"]}]"}');
insert into Product(`id`, `name`, `num`, `p_desc`, `image`, `price`, `state`, `createTime`, `category_id`) values('1', '变形精钢1', '10', '儿童玩具1', 'http://img30.360buyimg.com/popWaterMark/jfs/t1282/302/670265371/341800/bdbb2785/5598ba4fN2cb4fa3f.jpg', '100.0', '1', '1456221513', '3');
insert into Product(`id`, `name`, `num`, `p_desc`, `image`, `price`, `state`, `createTime`, `category_id`) values('2', '变形精钢2', '10', '儿童玩具2', 'http://img30.360buyimg.com/popWaterMark/jfs/t1282/302/670265371/341800/bdbb2785/5598ba4fN2cb4fa3f.jpg', '101.0', '1', '1456221513', '3');
insert into Product(`id`, `name`, `num`, `p_desc`, `image`, `price`, `state`, `salePrice`,`saleBeginTime`,`saleEndTime`, `createTime`, `category_id`) values('3', '变形精钢3', '10', '儿童玩具', 'http://img30.360buyimg.com/popWaterMark/jfs/t1282/302/670265371/341800/bdbb2785/5598ba4fN2cb4fa3f.jpg', '100.0', '1', '6.0', '1456221453', '1456307913', '1456221513', '3');
insert into Product(`id`, `productStyle_id`, `name`, `num`, `p_desc`, `image`, `price`, `state`, `extProperty`, `createTime`, `category_id`) values('4', '1', '变形精钢4', '10', '儿童玩具', 'http://img30.360buyimg.com/popWaterMark/jfs/t1282/302/670265371/341800/bdbb2785/5598ba4fN2cb4fa3f.jpg', '100.0', '1', '颜色:黄色,大小:L', '1456221513', '3');
insert into Product(`id`, `productStyle_id`, `name`, `num`, `p_desc`, `image`, `price`, `state`, `extProperty`, `createTime`, `category_id`) values('5', '1', '变形精钢5', '10', '儿童玩具', 'http://img30.360buyimg.com/popWaterMark/jfs/t1282/302/670265371/341800/bdbb2785/5598ba4fN2cb4fa3f.jpg', '100.0', '1', '颜色:红色,大小:M', '1456221513', '3');
