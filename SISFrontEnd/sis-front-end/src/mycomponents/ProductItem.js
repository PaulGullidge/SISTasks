import React, { useState } from 'react';
import ProductForm from './ProductForm';

const ProductItem = ({ product, updateProduct, deleteProduct }) => {
  const [isEditing, setIsEditing] = useState(false);

  const handleDelete = (e) => {
    if (window.confirm("Are you sure you wish to delete " + product.productName)) {
      deleteProduct(product.id);
    }
  }

  const formatPrice = (value) => {
    return value = (+value).toFixed(2);
  }

  return (
    <tr className={`product-item ${product.isSpecial ? 'special' : ''}`}>
      {isEditing ? (
        <td colSpan={6}>
        <ProductForm
          originalProduct={product}
          saveProduct={(updatedProduct) => {
            updateProduct(updatedProduct);
            setIsEditing(false);
          }}
        />
        </td>
      ) : (
        <>
          <td>{product.productName}</td>
          <td>{product.description}</td>
          {product.canExpire && <td>{product.expiryDate}</td>}
          {!product.canExpire && <td>N/A</td>}
          <td>{product.category}</td>
          <td>£{formatPrice(product.price)}</td>
          <td><button onClick={() => setIsEditing(true)}>Edit</button>&nbsp;
          <button onClick={handleDelete}>Delete</button></td>
        </>
      )}
    </tr>
  );
};

export default ProductItem;
