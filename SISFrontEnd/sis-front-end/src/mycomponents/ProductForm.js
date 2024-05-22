import React, { useState, useEffect } from 'react';

const ProductForm = ({ addProduct, originalProduct, saveProduct }) => {
  const [product, setProduct] = useState({
    id: originalProduct?.id || Date.now(),
    productName: originalProduct?.productName || '',
    description: originalProduct?.description || '',
    canExpire: originalProduct?.canExpire || false,
    expiryDate: originalProduct?.expiryDate || '',
    category: originalProduct?.category || '',
    price: originalProduct?.price || '0.00',
    isSpecial: originalProduct?.isSpecial || false,
  });

  useEffect(() => {
    if (originalProduct) {
      setProduct(originalProduct);
    }
  }, [originalProduct]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setProduct({
      ...product,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (originalProduct) {
      saveProduct(product);
    } else {
      addProduct(product);
    }
    setProduct({
      id: Date.now(),
      productName: '',
      description: '',
      canExpire: false,
      expiryDate: '',
      category: '',
      price: '',
      isSpecial: false,
    });
  };

  return (
    <form onSubmit={handleSubmit} className="product-form">
      <table align="center">
        <tr>
          <td>
      <input
        type="text"
        name="productName"
        placeholder="Product name"
        value={product.productName}
        onChange={handleChange}
        required
      />
          </td>
          <td>
      <input
        type="text"
        name="description"
        placeholder="Description"
        value={product.description}
        onChange={handleChange}
        required
      />
          </td>
          <td>
      <label>
        Can Expire:
        <input
          type="checkbox"
          name="canExpire"
          checked={product.canExpire}
          onChange={handleChange}
        />
      </label>
          </td>
          <td>
      {product.canExpire && (
        <input
          type="date"
          name="expiryDate"
          value={product.expiryDate}
          onChange={handleChange}
        />
      )}
        </td>
        <td>
      <input
        type="text"
        name="category"
        placeholder="Category"
        value={product.category}
        onChange={handleChange}
        required
      />
          </td>
          <td>
      <input
        type="number"
        min="0.00"
        step="any"
        name="price"
        placeholder="Price"
        value={product.price}
        onChange={handleChange}
        required
      />
          </td>
          <td>
      <label>
        Special:
        <input
          type="checkbox"
          name="isSpecial"
          checked={product.isSpecial}
          onChange={handleChange}
        />
      </label>
          </td>
          <td>
      <button type="submit">{originalProduct ? 'Save' : 'Add'}</button>
           </td>
        </tr>
      </table>
    </form>
  );
};

export default ProductForm;
