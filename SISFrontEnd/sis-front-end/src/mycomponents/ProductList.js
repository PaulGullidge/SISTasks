import React from 'react';
import ProductItem from './ProductItem';

const ProductList = ({ products, filter, updateProduct, deleteProduct }) => {
  return (
    <>
      {products
        .filter(product => filter === '' || product.category === filter)
        .map(product => (
          <ProductItem
            key={product.id}
            product={product}
            updateProduct={updateProduct}
            deleteProduct={deleteProduct}
          />
        ))}
    </>
  );
};

export default ProductList;
