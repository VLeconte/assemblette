<script setup lang="ts">
import type { Vote } from '@/entities/vote';
import type { PropType } from 'vue';
import DeputyCard from './DeputyCard.vue';
defineProps({
  name: {
    type: String,
    required: true
  },
  votes: {
    type: Object as PropType<Vote[]>,
    required: true
  }
});

</script>

<template>
  <div class="flex flex-col gap-y-1 gap-x-2 rounded-xl bg-white m-6 p-3 shadow-lg outline outline-black/5">
    <div class="text-lg text-gray-700">
      <p>{{ name }} </p>
    </div>
    <DeputyCard v-for="vote in votes" :key="vote.id" :deputy="vote.deputy" />
  </div>
</template>
