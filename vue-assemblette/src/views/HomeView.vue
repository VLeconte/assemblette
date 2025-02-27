<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import DeputyCard from '@/components/DeputyCard.vue';
import { type Deputy } from '@/entities/deputy';
import { useDeputiesStore } from '@/store/store-deputies';
import { useMandatesStore } from '@/store/store-mandates';
import type { Mandate } from '@/entities/mandate';

const deputiesStore = useDeputiesStore()
const mandatesStore = useMandatesStore()


const deputies = reactive<{
  data: Deputy[],
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

const mandatesByDeputies = reactive<{
  data: Record<string, Mandate[]>,
  isLoading: boolean
}>({
  data: {},
  isLoading: true
});

onMounted(
  async () => {
    deputies.data = await deputiesStore.getDeputies()
    deputies.isLoading = false
    mandatesByDeputies.data = await mandatesStore.getMandatesByDeputies()
    mandatesByDeputies.isLoading = false
  }
);

</script>

<template>
  <div v-if="!deputies.isLoading && !mandatesByDeputies.isLoading"
    class="w-screen flex flex-wrap gap-8 p-4 justify-center">
    <DeputyCard v-for="deputy in deputies.data" :key="deputy.id" :deputy="deputy"
      :mandates="mandatesByDeputies.data[deputy.id]" />
  </div>
</template>
