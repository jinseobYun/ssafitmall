<template>
    <div class="modal" v-if="visible">
      <div class="modal-overlay" @click="close"></div>
      <div class="modal-content">
        <button class="modal-close" @click="close">&times;</button>
        <slot></slot>
      </div>
    </div>
  </template>
  
  <script setup>
  import { defineProps, defineEmits } from 'vue';
  
  const props = defineProps({
    visible: Boolean,
  });
  
  const emits = defineEmits(['close']);
  
  const close = () => {
    emits('close');
  };
  </script>
  
  <style scoped>
  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: rgba(0, 0, 0, 0.5);
    z-index: 1000;
  }
  
  .modal-overlay {
    position: absolute;
    width: 100%;
    height: 100%;
  }
  
  .modal-content {
    position: relative;
    background: white;
    padding: 20px;
    border-radius: 10px;
    max-width: 500px;
    width: 100%;
  }
  
  .modal-close {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 1.5em;
    cursor: pointer;
  }
  </style>
  