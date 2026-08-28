// Cross-origin isolation headers, required for SharedArrayBuffer, which the
// sqlite-wasm worker depends on.
//
// `config.devServer` only exists for the dev-server tasks
// (wasmJsBrowserDevelopmentRun). Production webpack builds
// (wasmJsBrowserProductionWebpack, used by wasmJsBrowserDistribution) have no
// devServer, and assigning into it there throws:
//
//     TypeError: Cannot set properties of undefined (setting 'headers')
//
// For a deployed build these two headers must be sent by whatever serves the
// files; webpack cannot set them for you.
;(function (config) {
  if (!config.devServer) return

  config.devServer.headers = [
    { key: 'Cross-Origin-Opener-Policy', value: 'same-origin' },
    { key: 'Cross-Origin-Embedder-Policy', value: 'require-corp' },
  ]
})(config)
